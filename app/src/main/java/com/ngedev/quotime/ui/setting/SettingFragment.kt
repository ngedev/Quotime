package com.ngedev.quotime.ui.setting

import android.R.attr
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.*
import com.dokter.ai.util.SpHelp
import com.ngedev.quotime.Quote
import com.ngedev.quotime.R
import com.ngedev.quotime.databinding.FragmentAddEditBinding
import com.ngedev.quotime.databinding.FragmentSettingBinding
import com.ngedev.quotime.util.Cons
import java.util.concurrent.TimeUnit
import androidx.work.ExistingPeriodicWorkPolicy

import android.R.attr.tag

import androidx.work.WorkManager

import androidx.work.PeriodicWorkRequest
import com.ngedev.quotime.RandomQuotesWorker


class SettingFragment : Fragment() {

    private var _binding : FragmentSettingBinding?=null
    private val binding get() = _binding!!
    val spHepl by lazy { SpHelp(requireContext()) }

    private val workManager by lazy { activity?.applicationContext?.let { WorkManager.getInstance(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sNotifSetting.isChecked = spHepl.getBool(Cons.SHOW_NOTIFICATION)

        binding.sNotifSetting.setOnCheckedChangeListener { _, isChecked ->
            spHepl.writeBool(Cons.SHOW_NOTIFICATION, isChecked)

            when(isChecked){
                true -> {
                    scheduleNotif()
                }

                false -> {
                    disableNotif()
                }
            }
        }
    }

    fun scheduleNotif(){
        val request = PeriodicWorkRequest.Builder(
            RandomQuotesWorker::class.java, 23, TimeUnit.HOURS
        ).build()

        workManager?.enqueueUniquePeriodicWork(Cons.WORK_TAG, ExistingPeriodicWorkPolicy.REPLACE, request)
    }

    fun disableNotif(){
        workManager?.cancelAllWorkByTag(Cons.WORK_TAG)
    }

}