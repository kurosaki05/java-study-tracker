package com.example.studytracker.repository;

// 学習記録を表すStudyRecordクラスを使用するために読み込みます
import com.example.studytracker.entity.StudyRecord;

// データベースを簡単に操作するためのJpaRepositoryを読み込みます
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * StudyRecordをデータベースへ保存・取得するためのRepositoryです。
 *
 * Repositoryは、Javaアプリとデータベースの間に立って、
 * データの登録・検索・更新・削除を行います。
 *
 * JpaRepositoryを継承することで、
 * SQLを直接書かなくても基本的なデータベース操作ができます。
 */
public interface StudyRecordRepository
        extends JpaRepository<StudyRecord, Long> {

    /*
     * 現時点では、この中にメソッドを書く必要はありません。
     *
     * JpaRepositoryを継承するだけで、次のようなメソッドを
     * 自動的に使用できるようになります。
     *
     * save()       ：データを登録または更新する
     * findAll()    ：全データを取得する
     * findById()   ：IDを指定して1件取得する
     * deleteById() ：IDを指定して削除する
     * count()      ：保存されているデータ数を取得する
     */
}