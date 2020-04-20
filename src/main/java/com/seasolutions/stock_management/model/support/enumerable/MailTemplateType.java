package com.seasolutions.stock_management.model.support.enumerable;

public enum MailTemplateType {
    SEND_ADMIN("template_send_admin"),
    SEND_USER("template_send_user");


    private final String pathToTemplate;

    MailTemplateType(final String pathToTemplate) {
        this.pathToTemplate = pathToTemplate;
    }

    public String getPathToTemplate() {
        return pathToTemplate;
    }
}
