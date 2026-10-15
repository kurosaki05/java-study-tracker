package com.example.studytracker.entity;

// 日付を扱うためのクラス
// 例：2026-10-15
import java.time.LocalDate;

// このクラスをデータベースのテーブルとして扱うためのアノテーション
import jakarta.persistence.Entity;

// IDを自動で採番するために使用するアノテーション
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// データベース上の主キーを指定するアノテーション
import jakarta.persistence.Id;

/**
 * 学習記録を表すクラスです。
 *
 * 1件分の学習記録として、以下の情報を保持します。
 * ・学習日
 * ・科目
 * ・回答数
 * ・正解数
 * ・学習時間
 * ・メモ
 *
 * @Entityを付けることで、
 * このクラスのデータをH2 Databaseへ保存できるようになります。
 */
@Entity
public class StudyRecord {

    /**
     * 学習記録を識別するための番号です。
     *
     * @Id：
     * この項目をデータベースの主キーとして使用します。
     *
     * @GeneratedValue：
     * データを登録するときに、IDを自動で割り当てます。
     *
     * GenerationType.IDENTITY：
     * 1、2、3……のように、データベース側で連番を作ります。
     *
     * Long型は、整数を扱うための型です。
     * idがまだ設定されていない状態をnullで表現できるため、
     * 基本データ型のlongではなくLongを使用しています。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学習した日付です。
     *
     * LocalDateは、時刻を含まない日付を扱います。
     * 例：2026-10-15
     */
    private LocalDate studyDate;

    /**
     * 学習した科目です。
     *
     * String型は、文字列を扱うための型です。
     * 例：アルゴリズム、データベース、ネットワーク
     */
    private String subject;

    /**
     * 回答した問題数です。
     *
     * int型は、整数を扱う基本データ型です。
     * 例：50問
     */
    private int questionCount;

    /**
     * 正解した問題数です。
     *
     * 例：40問
     */
    private int correctCount;

    /**
     * 学習時間です。
     *
     * 単位は「分」とします。
     * 例：90分
     */
    private int studyMinutes;

    /**
     * 学習内容や間違えた原因などを記録するメモです。
     *
     * 例：擬似言語のループ処理を重点的に復習する
     */
    private String memo;

    /**
     * 引数を持たないコンストラクターです。
     *
     * コンストラクターとは、
     * StudyRecordオブジェクトを作るときに実行される処理です。
     *
     * Spring Data JPAがデータベースの情報から
     * StudyRecordオブジェクトを作る際に必要になります。
     */
    public StudyRecord() {
    }

    /**
     * IDを取得するgetterメソッドです。
     *
     * returnによって、idの値を呼び出し元へ返します。
     *
     * @return 学習記録のID
     */
    public Long getId() {
        return id;
    }

    /**
     * IDを設定するsetterメソッドです。
     *
     * 引数で受け取ったidを、
     * this.idへ代入しています。
     *
     * this.id：
     * このクラスが持っているフィールドのid
     *
     * id：
     * メソッドの引数として受け取ったid
     *
     * @param id 設定するID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 学習日を取得します。
     *
     * @return 学習日
     */
    public LocalDate getStudyDate() {
        return studyDate;
    }

    /**
     * 学習日を設定します。
     *
     * @param studyDate 設定する学習日
     */
    public void setStudyDate(LocalDate studyDate) {
        this.studyDate = studyDate;
    }

    /**
     * 科目名を取得します。
     *
     * @return 科目名
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 科目名を設定します。
     *
     * @param subject 設定する科目名
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 回答した問題数を取得します。
     *
     * @return 回答した問題数
     */
    public int getQuestionCount() {
        return questionCount;
    }

    /**
     * 回答した問題数を設定します。
     *
     * @param questionCount 設定する回答数
     */
    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    /**
     * 正解数を取得します。
     *
     * @return 正解数
     */
    public int getCorrectCount() {
        return correctCount;
    }

    /**
     * 正解数を設定します。
     *
     * @param correctCount 設定する正解数
     */
    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    /**
     * 学習時間を取得します。
     *
     * @return 学習時間（分）
     */
    public int getStudyMinutes() {
        return studyMinutes;
    }

    /**
     * 学習時間を設定します。
     *
     * @param studyMinutes 設定する学習時間（分）
     */
    public void setStudyMinutes(int studyMinutes) {
        this.studyMinutes = studyMinutes;
    }

    /**
     * メモを取得します。
     *
     * @return 学習メモ
     */
    public String getMemo() {
        return memo;
    }

    /**
     * メモを設定します。
     *
     * @param memo 設定する学習メモ
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 正答率を計算します。
     *
     * 計算式：
     * 正解数 ÷ 回答数 × 100
     *
     * 例：
     * 50問中40問正解した場合
     * 40 ÷ 50 × 100 = 80.0%
     *
     * @return 正答率
     */
    public double getCorrectRate() {

        /*
         * 回答数が0の場合、そのまま割り算すると
         * 0で割る処理になってしまいます。
         *
         * そのため、回答数が0なら先に0を返します。
         */
        if (questionCount == 0) {
            return 0;
        }

        /*
         * correctCountとquestionCountはint型です。
         *
         * int同士で割り算すると、小数部分が切り捨てられます。
         * そこで、correctCountをdouble型へ変換しています。
         *
         * (double)は「キャスト」と呼ばれ、
         * データ型を一時的に変換する処理です。
         */
        return (double) correctCount / questionCount * 100;
    }
}