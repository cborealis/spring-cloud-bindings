/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.bindings;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Binding")
final class BindingTest {

    @Test
    @DisplayName("populates content from filesystem")
    void test() {
        Binding binding = new Binding(Paths.get("src/test/resources/test-name-1"));

        assertThat(binding.getKind()).isEqualTo("test-kind-1");
        assertThat(binding.getProvider()).isEqualTo("test-provider-1");
        assertThat(binding.getMetadataFilePath("test-key"))
                .isEqualTo(Paths.get("src/test/resources/test-name-1/metadata/test-key"));
        assertThat(binding.getSecretFilePath("test-key"))
                .isEqualTo(Paths.get("src/test/resources/test-name-1/secret/test-key"));
    }

    @Test
    @DisplayName("populates k8s style content from filesystem")
    void testK8s() {
        //When bindings are provided as a k8s configmap secret pairs data files will be symlinks to hidden directories
        Binding binding = new Binding(Paths.get("src/test/resources/test-k8s"));

        assertThat(binding.getKind()).isEqualTo("test-kind-1");
        assertThat(binding.getProvider()).isEqualTo("test-provider-1");
        assertThat(binding.getMetadataFilePath("test-key"))
                .isEqualTo(Paths.get("src/test/resources/test-k8s/metadata/test-key"));
        assertThat(binding.getSecretFilePath("test-key"))
                .isEqualTo(Paths.get("src/test/resources/test-k8s/secret/test-key"));
    }
}
