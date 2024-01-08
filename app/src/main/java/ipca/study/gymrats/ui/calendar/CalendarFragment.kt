package ipca.study.gymrats.ui.calendar

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CalendarView
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import ipca.study.gymrats.R
import ipca.study.gymrats.repository.AppDatabase
import ipca.study.gymrats.repository.Planos
import ipca.study.gymrats.toShortDateTime
import java.util.Calendar
import java.util.Date

class CalendarFragment : Fragment() {

    var calendarPlanos :List<Planos> = arrayListOf<Planos>()

    val adapter = CalendarAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)

        val listView = rootView.findViewById<ListView>(R.id.listViewCalendar)
        listView.adapter = adapter

        val calendarView = rootView.findViewById<CalendarView>(R.id.calendar_view)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val planosDao = AppDatabase.getDatabase(requireContext())?.getPlanosDao()
        planosDao?.getAll()?.observe(viewLifecycleOwner, Observer {
            calendarPlanos = it.sortedBy { plano -> plano.date }
            adapter.notifyDataSetChanged()
        })
    }

    inner class CalendarAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return calendarPlanos.count()
        }

        override fun getItem(p0: Int): Any {
            return calendarPlanos[p0]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_calendar, parent, false)
            val textViewTipoTreino = rootView.findViewById<TextView>(R.id.textViewTrainingCalendar)
            val textViewDataPlano = rootView.findViewById<TextView>(R.id.textViewDateCalendar)

            textViewTipoTreino.text = calendarPlanos[position].trainingType
            textViewDataPlano.text = calendarPlanos[position].date.toShortDateTime()

            return rootView
        }
    }

}