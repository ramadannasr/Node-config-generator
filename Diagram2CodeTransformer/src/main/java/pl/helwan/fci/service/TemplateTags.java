package pl.helwan.fci.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.helwan.fci.dictionary.TemplateTagsEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateTags {

    private static TemplateTags INSTANCE = new TemplateTags();

    private static final String TAG_PREFIX = "<!--";
    private static final String TAG_SUFFIX = "-->";

    public String buildTemplateTag(TemplateTagsEnum tag) {
        return buildTemplateTag(tag.toString());
    }

    public String buildTemplateTag(String tag) {
        return TAG_PREFIX + tag + TAG_SUFFIX;
    }

    public static TemplateTags getInstance() {
        return INSTANCE;
    }
}
