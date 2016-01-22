package sealion.ui;

import sealion.domain.TaskStatus;

public class UIHelper {

    public String cssClass(TaskStatus status) {
        switch (status) {
        case OPEN:
            return "aui-lozenge aui-lozenge-success";
        case DOING:
            return "aui-lozenge aui-lozenge-current";
        case DONE:
            return "aui-lozenge aui-lozenge-error";
        case CLOSED:
            return "aui-lozenge aui-lozenge-default";
        }
        return "";
    }
}
