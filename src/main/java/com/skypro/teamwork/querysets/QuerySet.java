package com.skypro.teamwork.querysets;

import java.util.List;
import java.util.UUID;

public interface QuerySet {

    boolean checkRule(List<String> arguments, UUID userId);

    String getQueryType();

}
