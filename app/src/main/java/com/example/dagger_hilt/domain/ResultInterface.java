package com.example.dagger_hilt.domain;

import com.example.dagger_hilt.sys.util.Resource;

public interface ResultInterface {
    void invoke(Resource<String> resource);
}
