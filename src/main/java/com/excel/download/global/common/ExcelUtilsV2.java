package com.excel.download.global.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public final class ExcelUtilsV2 implements ExcelSupportV2 {

    private static final int MAX_ROW = 5000;
    private SXSSFWorkbook workbook;
    private HttpServletResponse response;

    @Override
    public void connect(HttpServletResponse response) {
        workbook = new SXSSFWorkbook(-1);
        this.response = response;
    }

    @Override
    public void draw(int sheetNum, Class<?> clazz, List<?> data) {
        try {
            getWorkBook(sheetNum, clazz, findHeaderNames(clazz), data);
            data.clear();
        } catch (IOException | IllegalAccessException e) {
            log.error("Excel Download Error Message = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(String fileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private SXSSFWorkbook getWorkBook(int sheetNum, Class<?> clazz, List<String> headerNames, List<?> data) throws IllegalAccessException, IOException {
        // 각 시트 당 MAX_ROW 개씩
        String sheetName = "Sheet" + (sheetNum + 1);

        Sheet sheet = ObjectUtils.isEmpty(this.workbook.getSheet(sheetName)) ? this.workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
        sheet.setDefaultColumnWidth((short) 300);   // 디폴트 너비 설정
        sheet.setDefaultRowHeight((short) 500);     // 디폴트 높이 설정

        Row row = null;
        Cell cell = null;

        row = sheet.createRow(0);
        createHeaders(row, cell, headerNames);
        createBody(clazz, data, sheet, row, cell);

        // 주기적인 flush 진행
        ((SXSSFSheet) sheet).flushRows(MAX_ROW);
        return this.workbook;
    }

    private void createHeaders(Row row, Cell cell, List<String> headerNames) {
        /**
         * header font style
         */
        Font font = this.workbook.createFont();
        font.setColor((short) 255);

        /**
         * header cell style
         */
        CellStyle headerCellStyle = this.workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);       // 가로 가운데 정렬
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 가운데 정렬

        // 테두리 설정
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);

        // 배경 설정
        headerCellStyle.setFillForegroundColor((short) 102);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(font);

        for (int i = 0, size = headerNames.size(); i < size; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerCellStyle);
            cell.setCellValue(headerNames.get(i));
        }
    }

    private void createBody(Class<?> clazz, List<?> data, Sheet sheet, Row row, Cell cell) throws IllegalAccessException {
        int startRow = 0;
        for (Object o : data) {
            List<Object> fields = findFieldValue(clazz, o);
            row = sheet.createRow(++startRow);
            for (int i = 0, fieldSize = fields.size(); i < fieldSize; i++) {
                cell = row.createCell(i);
                cell.setCellValue(String.valueOf(fields.get(i)));
            }
        }
    }

    /**
     * 엑셀의 헤더 명칭을 찾는 로직
     */
    private List<String> findHeaderNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumnName.class))
                .map(field -> field.getAnnotation(ExcelColumnName.class).name())
                .collect(Collectors.toList());
    }

    /**
     * 데이터의 값을 추출하는 메서드
     */
    private List<Object> findFieldValue(Class<?> clazz, Object obj) throws IllegalAccessException {
        List<Object> result = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            result.add(field.get(obj));
        }
        return result;
    }
}
