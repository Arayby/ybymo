package com.arayby.ybymo.core.extractors;

import com.arayby.ybymo.core.models.DataRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Extractor<S> {

    List<DataRecord> extract(Path file, List<S> selectors) throws IOException;
}
