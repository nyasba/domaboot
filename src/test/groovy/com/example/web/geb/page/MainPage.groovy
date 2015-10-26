package com.example.web.geb.page

import geb.Page

/**
 * メインページ
 */
class MainPage extends Page {

    // to MainPage でアクセスするURLを定義
    static url = "http://localhost:8888/customers"

    // at MainPage で満たすべきassert条件を記載
    static at = { title == "顧客管理システム" }

    // MainPageのコンテンツ
    static content = {

        姓は { $('#lastName').value(it) }
        名は { $('#firstName').value(it) }
        で登録する { $('#register').click() }

        登録件数 {
            $('#customer-list').find("tbody").children().size()
        }

        名前 {
            $("#lastName${it}").text() + $("#firstName${it}").text()
        }

        登録なし {
            $("#id${it}").text() == null
        }

        編集ボタンを押す(to: EditPage){
            $("#edit${it}").click()
        }

        削除ボタンを押す(to: MainPage){
            $("#delete${it}").click()
        }
    }

}
