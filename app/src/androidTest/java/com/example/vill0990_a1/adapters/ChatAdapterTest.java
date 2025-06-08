package com.example.vill0990_a1.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import com.example.vill0990_a1.R;

@RunWith(AndroidJUnit4.class)
public class ChatAdapterTest {

    private ChatAdapter adapter;
    private List<String> testMessages;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        testMessages = Arrays.asList("Hello", "How are you?", "I'm fine");
        adapter = new ChatAdapter(context, testMessages);
    }

    @Test
    public void testGetCount_returnsCorrectSize() {
        assertEquals(3, adapter.getCount());
    }

    @Test
    public void testGetItem_returnsCorrectItem() {
        assertEquals("Hello", adapter.getItem(0));
        assertEquals("I'm fine", adapter.getItem(2));
    }

    @Test
    public void testGetView_inflatesCorrectLayoutAndSetsText() {
        // Simulate parent ViewGroup (can be a simple FrameLayout)
        android.widget.FrameLayout parent = new android.widget.FrameLayout(context);

        // Test view at position 0 (even = incoming)
        View view0 = adapter.getView(0, null, parent);
        TextView message0 = view0.findViewById(R.id.messageText);
        assertNotNull("TextView should not be null", message0);
        assertEquals("Hello", message0.getText().toString());

        // Test view at position 1 (odd = outgoing)
        View view1 = adapter.getView(1, null, parent);
        TextView message1 = view1.findViewById(R.id.messageText);
        assertNotNull("TextView should not be null", message1);
        assertEquals("How are you?", message1.getText().toString());
    }
}
