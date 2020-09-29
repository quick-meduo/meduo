/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.mask;

import org.quick.meduo.mask.annotation.Masking;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.HashSet;
import java.util.Set;

/**
 * Masking Filter Annotation FormatterFactory
 */
public class MaskingAnnotationFormatterFactory implements AnnotationFormatterFactory<Masking> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> hashSet = new HashSet<>();
        hashSet.add(String.class);
        return hashSet;
    }

    @Override
    public Printer<?> getPrinter(Masking desensitized, Class<?> aClass) {
        return getFormatter(desensitized);
    }

    @Override
    public Parser<?> getParser(Masking desensitized, Class<?> aClass) {
        return getFormatter(desensitized);
    }


    private MaskingFormatter getFormatter(Masking desensitized) {
        MaskingFormatter formatter = new MaskingFormatter();
        formatter.setTypeEnum(desensitized.value());
        return formatter;
    }
}
