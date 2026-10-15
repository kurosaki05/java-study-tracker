package com.example.studytracker.controller;

// 学習記録を表すEntity
import com.example.studytracker.entity.StudyRecord;

// 学習記録のデータベース操作を担当するRepository
import com.example.studytracker.repository.StudyRecordRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 学習記録に関する画面表示と処理を担当するControllerです。
 *
 * 主な役割：
 * ・学習記録の一覧表示
 * ・新規登録画面の表示
 * ・学習記録の登録
 * ・編集画面の表示
 * ・学習記録の更新
 * ・学習記録の削除
 */
@Controller
public class StudyRecordController {

    /**
     * StudyRecordをデータベースに保存・取得するRepositoryです。
     */
    private final StudyRecordRepository studyRecordRepository;

    /**
     * コンストラクターです。
     *
     * SpringがStudyRecordRepositoryを自動的に渡します。
     * この仕組みを「依存性の注入（DI）」と呼びます。
     *
     * @param studyRecordRepository 学習記録を操作するRepository
     */
    public StudyRecordController(
            StudyRecordRepository studyRecordRepository) {

        this.studyRecordRepository = studyRecordRepository;
    }

    /**
     * 学習記録の一覧画面を表示します。
     *
     * URL：
     * http://localhost:8080/records
     *
     * @param model HTMLへデータを渡すためのオブジェクト
     * @return 表示するHTMLファイル名
     */
    @GetMapping("/records")
    public String showRecordList(Model model) {

        // データベースからすべての学習記録を取得します
        model.addAttribute(
                "records",
                studyRecordRepository.findAll()
        );

        // record-list.htmlを表示します
        return "record-list";
    }

    /**
     * 学習記録の新規登録画面を表示します。
     *
     * URL：
     * http://localhost:8080/records/new
     *
     * @param model HTMLへデータを渡すためのオブジェクト
     * @return 表示するHTMLファイル名
     */
    @GetMapping("/records/new")
    public String showRecordForm(Model model) {

        // 入力フォーム用の空のStudyRecordを作ります
        model.addAttribute(
                "studyRecord",
                new StudyRecord()
        );

        /*
         * 新規登録か編集かを画面側で判断するため、
         * modeという名前でcreateを渡します。
         */
        model.addAttribute("mode", "create");

        // record-form.htmlを表示します
        return "record-form";
    }

    /**
     * 入力された学習記録を新規登録します。
     *
     * @param studyRecord 入力フォームから受け取った学習記録
     * @return 登録後の移動先
     */
    @PostMapping("/records")
    public String saveRecord(StudyRecord studyRecord) {

        // 学習記録をデータベースへ保存します
        studyRecordRepository.save(studyRecord);

        // 登録後は一覧画面へ移動します
        return "redirect:/records";
    }

    /**
     * 学習記録の編集画面を表示します。
     *
     * URL例：
     * http://localhost:8080/records/1/edit
     *
     * {id}の部分には、編集対象のIDが入ります。
     *
     * @param id 編集する学習記録のID
     * @param model HTMLへデータを渡すためのオブジェクト
     * @return 表示するHTMLファイル名
     */
    @GetMapping("/records/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        /*
         * findById(id)で、指定されたIDの学習記録を検索します。
         *
         * orElseThrow()は、対象データが存在しない場合に
         * 例外を発生させる処理です。
         */
        StudyRecord studyRecord = studyRecordRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "指定された学習記録が見つかりません。ID: " + id
                        )
                );

        /*
         * データベースから取得した学習記録を、
         * studyRecordという名前でHTMLへ渡します。
         *
         * これによって、現在の値が編集フォームへ表示されます。
         */
        model.addAttribute("studyRecord", studyRecord);

        // 編集画面として表示することをHTMLへ伝えます
        model.addAttribute("mode", "edit");

        // 新規登録と同じrecord-form.htmlを使用します
        return "record-form";
    }

    /**
     * 編集された学習記録を更新します。
     *
     * URL例：
     * POST /records/1/update
     *
     * @param id 更新する学習記録のID
     * @param formRecord 入力フォームから受け取った内容
     * @return 更新後の移動先
     */
    @PostMapping("/records/{id}/update")
    public String updateRecord(
            @PathVariable Long id,
            StudyRecord formRecord) {

        /*
         * 更新前のデータをデータベースから取得します。
         *
         * いきなりフォームのデータを保存するのではなく、
         * 最初に対象データが存在するか確認しています。
         */
        StudyRecord existingRecord = studyRecordRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "更新対象の学習記録が見つかりません。ID: " + id
                        )
                );

        // フォームの学習日を更新対象へ設定します
        existingRecord.setStudyDate(formRecord.getStudyDate());

        // フォームの科目を更新対象へ設定します
        existingRecord.setSubject(formRecord.getSubject());

        // フォームの回答数を更新対象へ設定します
        existingRecord.setQuestionCount(
                formRecord.getQuestionCount()
        );

        // フォームの正解数を更新対象へ設定します
        existingRecord.setCorrectCount(
                formRecord.getCorrectCount()
        );

        // フォームの学習時間を更新対象へ設定します
        existingRecord.setStudyMinutes(
                formRecord.getStudyMinutes()
        );

        // フォームのメモを更新対象へ設定します
        existingRecord.setMemo(formRecord.getMemo());

        /*
         * IDを持つ既存データをsave()すると、
         * 新規登録ではなく更新として処理されます。
         */
        studyRecordRepository.save(existingRecord);

        // 更新後は一覧画面へ移動します
        return "redirect:/records";
    }

    /**
     * 指定された学習記録を削除します。
     *
     * URL例：
     * POST /records/1/delete
     *
     * @param id 削除する学習記録のID
     * @return 削除後の移動先
     */
    @PostMapping("/records/{id}/delete")
    public String deleteRecord(@PathVariable Long id) {

        /*
         * データが存在する場合だけ削除します。
         *
         * existsById()は、指定したIDのデータが
         * 存在するか確認するメソッドです。
         */
        if (studyRecordRepository.existsById(id)) {
            studyRecordRepository.deleteById(id);
        }

        // 削除後は一覧画面へ移動します
        return "redirect:/records";
    }
}