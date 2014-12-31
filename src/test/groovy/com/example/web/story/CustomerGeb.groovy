package com.example.web.story

import com.example.App
import com.example.web.page.EditPage
import com.example.web.page.MainPage
import geb.spock.GebSpec
import spock.lang.Stepwise
import spock.lang.Unroll

@Unroll      // メソッド名に変数を差し込めるようにする
@Stepwise    // 上から順に実行するようにする
class CustomerGeb extends GebSpec {

    def setupSpec() {
        App.main([
                "--server.port=8888",
                "--spring.profiles.active=test"
        ] as String[])
    }

    // whereブロックの変数名を引数に設定。それらをINPUTにメソッドが繰り返し呼ばれる
    def "「#lastName#firstName」を登録する"(int num, String lastName, String firstName) {

        setup:
            to MainPage
            at MainPage
            assert 登録件数 == num -1

        expect:
            姓は lastName
            名は firstName
            で登録する

            assert 登録件数 == num
            assert 名前(num) == lastName + firstName

        // テストに必要なINPUTを定義するブロック
        where:
            num | lastName | firstName
            1   | "剛田"     | "たけし"
            2   | "どら"     | "えもん"
    }

    def "_#num番目を「#lastName#firstName」に変更する"(int num, String lastName, String firstName){

        setup:
            to MainPage
            at MainPage

        expect:
            編集ボタンを押す(num)

            at EditPage

            姓は lastName
            名は firstName
            で更新する

            at MainPage
            assert 名前(num) == lastName + firstName

        where:
            num | lastName | firstName
            1   | "きれいな"     | "ジャイアン"
    }

    def "_#num番目を「#lastName#firstName」に変更しようとしてやっぱり戻る"(int num, String lastName, String firstName){

        setup:
            to MainPage
            at MainPage

        expect:
            def 編集前の名前 = 名前(num)
            編集ボタンを押す(num)

            at EditPage

            姓は lastName
            名は firstName
            やっぱり戻る

            at MainPage
            assert 名前(num) == 編集前の名前

        where:
            num | lastName | firstName
            2   | "どら"     | "みちゃん"
    }

    def "_1番目を削除する"(){

        setup:
            to MainPage
            at MainPage

        when:
            assert 登録件数 == 2
            def 編集前の名前 = 名前(2)
            削除ボタンを押す(1)

        then:
            at MainPage
            assert 登録件数 == 1
            assert 名前(1) == 編集前の名前
    }

}
