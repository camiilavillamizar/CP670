package com.example.vill0990_a1;

import static org.junit.Assert.*;

import android.content.Context;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ChatWindowTest {

    private ChatWindow chatWindow;
    private ChatWindow.ChatAdapter adapter;

    // Mocked context (required by ArrayAdapter constructor)
    private Context mockContext;

    @Before
    public void setUp() {
        // Create a new instance of the activity (non-UI context)
        chatWindow = new ChatWindow();

        // Simulate a Context with Mockito
        mockContext = mock(Context.class);

        // Manually add some messages to the messages list
        chatWindow.messages = new ArrayList<>();
        chatWindow.messages.add("Hello");
        chatWindow.messages.add("World");

        // Create the adapter instance with the mocked context
        adapter = chatWindow.new ChatAdapter(mockContext);
    }

    @Test
    public void testAdapterCount_ReturnsCorrectSize() {
        // Adapter should report 2 items
        assertEquals(2, adapter.getCount());
    }

    @Test
    public void testAdapterGetItem_ReturnsCorrectItem() {
        // Should match the manually added values
        assertEquals("Hello", adapter.getItem(0));
        assertEquals("World", adapter.getItem(1));
    }

    @Test
    public void testAdapterGetItem_OutOfBounds() {
        // Should throw IndexOutOfBoundsException if invalid index
        try {
            adapter.getItem(2); // Index 2 doesn't exist
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // Expected result
        }
    }

    @Test
    public void testMessagesAdd_IncreasesCount() {
        int initialCount = adapter.getCount();

        chatWindow.messages.add("New message");

        assertEquals(initialCount + 1, adapter.getCount());
        assertEquals("New message", adapter.getItem(2));
    }

    @Test
    public void testMessagesClear_EmptiesAdapter() {
        chatWindow.messages.clear();

        assertEquals(0, adapter.getCount());
    }

}