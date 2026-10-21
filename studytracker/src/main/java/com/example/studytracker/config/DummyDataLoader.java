package com.example.studytracker.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.studytracker.entity.StudyRecord;
import com.example.studytracker.repository.StudyRecordRepository;

/**
 * 動作確認用のダミーデータを登録するクラスです。
 *
 * Spring Bootの起動時に処理が実行され、
 * データベースが空の場合だけ学習記録を登録します。
 */
@Configuration
public class DummyDataLoader {

    /**
     * CommandLineRunnerは、Spring Bootの起動完了後に
     * 特定の処理を実行するための仕組みです。
     *
     * @param repository 学習記録を操作するRepository
     * @return 起動時に実行する処理
     */
    @Bean
    CommandLineRunner loadDummyData(
            StudyRecordRepository repository) {

        return args -> {

            /*
             * すでにデータが登録されている場合は、
             * ダミーデータを追加しません。
             *
             * これにより、同じデータが何度も登録されるのを防ぎます。
             */
            if (repository.count() > 0) {
                return;
            }
            System.out.println("★★★★ DummyDataLoader 実行開始 ★★★★");

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 1),
                    "アルゴリズム",
                    20,
                    16,
                    60,
                    "繰り返し処理と配列を復習"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 2),
                    "データベース",
                    25,
                    20,
                    75,
                    "正規化について復習"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 3),
                    "ネットワーク",
                    30,
                    21,
                    90,
                    "TCP/IPの階層構造を確認"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 4),
                    "セキュリティ",
                    20,
                    18,
                    60,
                    "暗号化とハッシュ化の違いを整理"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 5),
                    "Java",
                    15,
                    12,
                    90,
                    "クラスとインスタンスを復習"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 6),
                    "ソフトウェア設計",
                    25,
                    17,
                    80,
                    "UMLの種類を確認"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 7),
                    "プロジェクト管理",
                    20,
                    15,
                    45,
                    "WBSとクリティカルパスを復習"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 8),
                    "経営戦略",
                    30,
                    24,
                    70,
                    "SWOT分析とPPMを確認"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 9),
                    "擬似言語",
                    20,
                    14,
                    100,
                    "二重ループの処理を重点的に復習"
            ));

            repository.save(createRecord(
                    LocalDate.of(2026, 10, 10),
                    "コンピュータ構成",
                    25,
                    20,
                    60,
                    "CPUとメモリの関係を復習"
            ));

                System.out.println("★★★★ DummyDataLoader 実行終了 ★★★★");
        };
    }

    /**
     * ダミーの学習記録を1件作成するメソッドです。
     *
     * 同じsetter処理を何度も書かなくてよいように、
     * 共通処理として切り出しています。
     *
     * @param studyDate 学習日
     * @param subject 科目
     * @param questionCount 回答数
     * @param correctCount 正解数
     * @param studyMinutes 学習時間
     * @param memo メモ
     * @return 作成した学習記録
     */
    private StudyRecord createRecord(
            LocalDate studyDate,
            String subject,
            int questionCount,
            int correctCount,
            int studyMinutes,
            String memo) {

        // 空の学習記録オブジェクトを作成します
        StudyRecord record = new StudyRecord();

        // 各項目へ引数で受け取った値を設定します
        record.setStudyDate(studyDate);
        record.setSubject(subject);
        record.setQuestionCount(questionCount);
        record.setCorrectCount(correctCount);
        record.setStudyMinutes(studyMinutes);
        record.setMemo(memo);

        // 完成した学習記録を呼び出し元へ返します
        return record;
    }
}