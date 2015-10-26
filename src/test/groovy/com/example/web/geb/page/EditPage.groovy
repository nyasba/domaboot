package com.example.web.geb.page

import geb.Page

/**
 * 編集ページ
 */
class EditPage extends Page {

    // at MainPage で満たすべきassert条件を記載
    static at = { title == "顧客情報編集" }

    // MainPageのコンテンツ
    static content = {

        姓は { $('#lastName').value(it) }
        名は { $('#firstName').value(it) }
        で更新する( to : MainPage) { $('#submit').click() }

        やっぱり戻る { $('#goToTop').click() }
    }

}
