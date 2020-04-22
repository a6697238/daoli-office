package com.daoli.office.server.controller.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * AUTO-GENERATED: houlu @ 2019/10/4 上午1:08
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class DaoLiDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        return new Date(Long.valueOf(source));
    }
}
