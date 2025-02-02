package com.learning.concurrent.collections.maps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

class PlayWithConcurrentHashMapTest {

    @Test
    void shouldComputeIfAbsent() {
        var sut = new ConcurrentHashMap<LLMName, Score>();
        var result = sut.computeIfAbsent(new LLMName("OpenAI-4o"), llmName -> new Score(96.6));
        Assertions.assertEquals(result, new Score(96.6));
    }

    private record LLMName(String name) {
        public LLMName {
            Objects.requireNonNull(name, "Invalid model name: "+name);
        }
    }
    private record Score(double score) {
        public Score {
            if(score < 0 || score > 100) {
                throw new IllegalArgumentException("Invalid score value: " + score);
            }
        }
    }
}