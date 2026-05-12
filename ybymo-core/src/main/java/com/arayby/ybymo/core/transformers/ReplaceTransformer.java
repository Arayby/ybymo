package com.arayby.ybymo.core.transformers;

import com.arayby.ybymo.core.messages.KeyMessage;
import com.arayby.ybymo.core.models.DataRecord;
import com.arayby.ybymo.core.validators.ArgumentValidator;

import java.util.List;

public class ReplaceTransformer implements Transformer {

    private final String textToReplace;
    private final String replacementText;

    public ReplaceTransformer(String textToReplace, String replacementText) {
        this.textToReplace = ArgumentValidator.requireNonEmpty(textToReplace, KeyMessage.PARAMETER_TEXT_TO_REPLACE);
        this.replacementText = ArgumentValidator.requireNonNull(replacementText, KeyMessage.PARAMETER_REPLACEMENT_TEXT);
    }

    @Override
    public DataRecord transform(DataRecord dataRecord) {
        List<String> fieldValues = dataRecord.fields().stream().map(DataRecord.Field::value).toList();
        ArgumentValidator.requireExistsInAny(textToReplace, fieldValues);

        List<DataRecord.Field> replaced = dataRecord.fields().stream().map(field -> {
            String value = field.value();
            return value == null ? field : field.withValue(value.replace(textToReplace, replacementText));
        }).toList();

        return dataRecord.withFields(replaced);
    }
}
