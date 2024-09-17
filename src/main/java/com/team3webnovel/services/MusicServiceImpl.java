package com.team3webnovel.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Override
    public String generateMusic(String prompt, boolean makeInstrumental) throws Exception {
        // Python 파일의 정확한 경로로 수정
        String pythonScriptPath = "C:\\Users\\Taewon_Kim\\eclipse-workspace\\team3webnovel\\src\\main\\resources\\suno\\suno_functions.py";

        // Python 명령어 실행 시 사용할 커맨드 리스트 생성
        List<String> command = new ArrayList<>();
        command.add("python"); // 필요 시 "python3"으로 변경
        command.add(pythonScriptPath);
        command.add(prompt);
        command.add(String.valueOf(makeInstrumental));

        // 서브프로세스를 통해 Python 스크립트 실행
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // stderr을 stdout으로 병합
        Process process = processBuilder.start();

        // 프로세스 출력값을 읽음
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        // 프로세스가 종료될 때까지 대기
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            // 비정상 종료 시 오류 처리
            throw new RuntimeException("Python 스크립트 실행 실패. 종료 코드: " + exitCode);
        }

        // 음악 URL 또는 오류 메시지 반환
        return output.toString();
    }
}
