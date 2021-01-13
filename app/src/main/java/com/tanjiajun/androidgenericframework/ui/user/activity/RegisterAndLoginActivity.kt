package com.tanjiajun.androidgenericframework.ui.user.activity

import android.os.Bundle
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityRegisterAndLoginBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.NoViewModel
import com.tanjiajun.androidgenericframework.ui.main.viewmodel.MainViewModel
import com.tanjiajun.androidgenericframework.ui.user.fragment.LoginFragment
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class RegisterAndLoginActivity : BaseActivity<ActivityRegisterAndLoginBinding, LoginViewModel>() {

    override val layoutRes: Int = R.layout.activity_register_and_login
    override val viewModel by lifecycleScope.viewModel<LoginViewModel>(this)
    override val containId: Int = R.id.fl_content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@RegisterAndLoginActivity
            viewModel = this@RegisterAndLoginActivity.viewModel
        }
        addFragment(LoginFragment.newInstance())
    }

}