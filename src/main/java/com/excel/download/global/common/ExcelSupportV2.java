package com.excel.download.global.common;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelSupportV2 {

    void connect(HttpServletResponse response);
    void draw(int sheetNum, Class<?> clazz, List<?> data);
    void download(String fileName);
}
