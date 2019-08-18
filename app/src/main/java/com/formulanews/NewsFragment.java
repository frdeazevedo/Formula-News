package com.formulanews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        TextView headline = (TextView)view.findViewById(R.id.text_view_header);
        headline.setText("Aqui vai o headline da notícia");

        TextView fullnews = (TextView)view.findViewById(R.id.text_view_news);
        fullnews.setText("Nascido em Emmerich am Rhein, Renânia do Norte-Vestfália, Alemanha Ocidental, Hülkenberg fez sua estréia de kart em 1997, aos 10 anos de idade. Em 2002, foi Campeão Alemão de Kart Júnior e no ano seguinte ganhou o Campeonato Alemão de Kart. Hülkenberg foi anteriormente gerenciado por Willi Weber, o gerente de longa data de Michael Schumacher. Weber previu que Hülkenberg estaria pronto para a Fórmula 1 até 2008. Ele também elogiou Hülkenberg como um \"talento inacreditável\" e disse que ele lembrou Schumacher é um jovem piloto. Ele também afirmou que ele o apelidou de \"The Hulk\", depois do super-herói fictício, em referência a Hülkenberg mudando sua personalidade ao volante.");

        return view;
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }
}
