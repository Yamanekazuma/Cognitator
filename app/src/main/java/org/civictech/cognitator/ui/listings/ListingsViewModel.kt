package org.civictech.cognitator.ui.listings

import androidx.compose.material3.DrawerState
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.civictech.cognitator.data.check.CheckService
import org.civictech.cognitator.data.checklist.CheckItemStatus
import org.civictech.cognitator.data.checklist.CheckItemWithStatus
import javax.inject.Inject

@HiltViewModel(assistedFactory = ListingsViewModelFactory::class)
class ListingsViewModel @AssistedInject constructor(
    @Assisted private val drawerState: DrawerState
) : ViewModel() {

    @Inject
    lateinit var checkService: CheckService

    fun getDrawerState(): DrawerState = drawerState

    fun getAllItemsWithStatus(): List<CheckItemWithStatus> = checkService.getList().map {
        CheckItemWithStatus(
            it.id,
            checkService.getCategory(it.categoryId),
            it.risk,
            it.label,
            it.description,
            it.isEnable,
            it.status
        )
    }.sortedBy { it.category }

    fun getAllItemsWithStatusMock(): List<CheckItemWithStatus> = listOf(
        CheckItemWithStatus(
            3,
            "Application",
            2,
            "Install apps from outside the App Store",
            "正規のアプリストア外からインストールされたアプリがあります。正規のアプリストア外で配布されているアプリは十分な検査が行われていないため、危険です。直ちにアンインストールしてください。",
            true,
            CheckItemStatus.None
        ),
        CheckItemWithStatus(
            6,
            "Application",
            1,
            "Can't found a MFA application",
            "多要素認証のためのアプリがインストールされていません。\n多要素認証を用いることで、不正ログインのリスクを大幅に減らすことができます。積極的に多要素認証を活用しましょう。",
            true,
            CheckItemStatus.Safe
        ),
        CheckItemWithStatus(
            5,
            "E-mail",
            1,
            "Open a link in an email without checking",
            "Eメール中のリンクのクリック前に十分な確認時間がみられません。\nEメールに含まれるURLはフィッシングサイトや危険なサイトである可能性があります。「https://～」で始まるか、正規のドメインかなどを確認したうえでクリックしてください。",
            true,
            CheckItemStatus.Dangerous
        ),
        CheckItemWithStatus(
            1,
            "Network",
            3,
            "Connect to free Wi-Fi frequently",
            "フリーWi-Fiを頻繁に利用しています。\nフリーWi-Fiには盗聴のリスクがあります。信頼のできる管理者の持つフリーWi-Fiにのみ接続するか、フリーWi-Fi利用中は貴重な情報は送信しないように注意してください。",
            false,
            CheckItemStatus.None
        ),
        CheckItemWithStatus(
            4,
            "Network",
            2,
            "Use HTTP frequently",
            "「http://～」から始まるサイトに頻繁にアクセスしています。これらのサイトへの通信は暗号化されません。\n通信内容の盗聴を防ぐため、日頃から「https://～」を使うよう意識しましょう。",
            false,
            CheckItemStatus.Safe
        ),
        CheckItemWithStatus(
            2,
            "System",
            3,
            "Use easy password on your smartphone",
            "使用中のスマートフォンはパスワード設定がされていないか、予測しやすいパスワードが設定されています。パスワードを簡単なものにしていると、端末紛失時の情報漏えいリスクが高まります。複雑なパスワードまたは生体認証の活用を検討してください。",
            false,
            CheckItemStatus.Dangerous
        )
    )
}
