package mumtaz.binar.challangechapterenam.view

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mumtaz.binar.challangechapterenam.R
import mumtaz.binar.challangechapterenam.model.GetAllUserItem
import mumtaz.binar.challangechapterenam.model.ResponseLogin
import mumtaz.binar.challangechapterenam.network.ApiClient
import mumtaz.binar.challangechapterenam.viewmodel.ViewModelUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    lateinit var dataUser : List<GetAllUserItem>
    lateinit var viewModel : ViewModelUser
    lateinit var email: String
    lateinit var password: String
    lateinit var toast : String
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_login, container, false)
        userManager = UserManager(requireContext())
        getDataUserItem()

        val daftar = view.findViewById<TextView>(R.id.registrasi)
        val login = view.findViewById<Button>(R.id.btnlogin)

        daftar.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        login.setOnClickListener {
            if (loginemail.text.isNotEmpty() && loginpassword.text.isNotEmpty()){
                email = loginemail.text.toString()
                password = loginpassword.text.toString()
                check(dataUser)
            }
            else{
                toast = "Harap Isi Semua Data"
                custom()
            }
        }
        return view
    }

    fun custom(){
        val text = toast
        val toast = Toast.makeText(
            requireActivity()?.getApplicationContext(),
            text,
            Toast.LENGTH_LONG
        )
        val text1 =
            toast.getView()?.findViewById(android.R.id.message) as TextView
        val toastView: View? = toast.getView()
        toastView?.setBackgroundColor(Color.TRANSPARENT)
        text1.setTextColor(Color.RED);
        text1.setTextSize(15F)
        toast.show()
        toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)
    }

    fun getDataUserItem(){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
            dataUser = it


        })
        viewModel.userApi()
    }

    fun check(dataUser : List<GetAllUserItem>) {
        userManager = UserManager(requireContext())
        login(email, password)
        for (i in dataUser.indices) {
            if (email == dataUser[i].email && password == dataUser[i].password) {

                GlobalScope.launch {
                    userManager.saveDataLogin("true")
                    userManager.saveDataUser(dataUser[i].id, dataUser[i].email,dataUser[i].username, dataUser[i].completeName,dataUser[i].dateofbirth, dataUser[i].address )
                }

                view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    fun login(email :String, password : String){
        ApiClient.instance.login(email, password).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful){

                    Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                }else{
                    toast = "Data yang dimasukkan salah!"
                    custom()
                }
            }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

            }
        })
    }


}