package com.ogzkesk.core.tflite;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.util.List;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import timber.log.Timber;

public class TextClassificationClient {
    private static final String MODEL_PATH = "model.tflite";
    private final Context context;

    NLClassifier classifier;

    public TextClassificationClient(Context context) {
        this.context = context;
    }

    public void load() {
        try {
            classifier = NLClassifier.createFromFile(context, MODEL_PATH);
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    public void unload() {
        classifier.close();
        classifier = null;
    }

    public List<Category> classify(String text) {
        return classifier.classify(text);
    }

}