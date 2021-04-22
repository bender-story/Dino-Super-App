package com.rahul.pod.login.network

import com.rahul.dino.core.network.NetworkConfigInterface
import com.rahul.dino.core.network.ServiceAPIHelper
import com.rahul.pod.login.data.LoginDataRequest
import com.rahul.pod.login.data.LoginDataResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class LoginServiceRepo : KoinComponent {
    private val networkConfig: NetworkConfigInterface by inject()
    private val serviceAPIHelper: ServiceAPIHelper<LoginServiceInterface,LoginMockServiceImpl> by inject { parametersOf(networkConfig.getServiceType(), networkConfig.getBaseURL()) }

    // initialise disposable object to dump api calls
    private val disposable: CompositeDisposable = CompositeDisposable()

    /**
     * get movies list from the service
     * @param onSuccess success callback
     * @param onSuccess error callback
     */
    fun login(
            userName: String,
            password: String,
            onSuccess: (LoginDataResponse) -> Unit,
            onError: (String) -> Unit
    ) {

        disposable.add(serviceAPIHelper.serviceInterface!!.login(LoginDataRequest(userName, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            onSuccess.invoke(result)
                        },
                        { error ->
                            onError.invoke(error.toString())
                        }
                ))
    }

    /**
     * method to dump calls and release memory
     */
    fun dispose() {
        disposable.dispose()
    }
}

