package com.formulanews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends    AppCompatActivity
                          implements BottomNavigationView.OnNavigationItemSelectedListener,
                                     FetchDataAsyncTask.OnJSONResponse {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.initGui();
        this.testing();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.closeAllFragments();

        switch(item.getItemId()) {
            case R.id.action_news: {
                break;
            }
            case R.id.action_standings: {
                break;
            }
            case R.id.action_videos: {
                break;
            }
        }

        return true;
    }

    public void initGui() {
        this.mBottomNavigationView = findViewById(R.id.bottom_navigation);
        this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void openFragment(Fragment[] fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int i=0;
        for(Fragment f:fragment) {
            i++;
            transaction.add(R.id.container, f, "Fragment"+i);
        }

        transaction.commit();
    }

    private void closeAllFragments() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }

    private void testing() {
        FetchDataAsyncTask fetchDataAsyncTask = new FetchDataAsyncTask(this);
        fetchDataAsyncTask.execute("https://jsonplaceholder.typicode.com/posts");

        /*News[] allnews = new News[10];

        for(int i=0; i<10; i++) {
            News n = new News("Da chance de substituir Felipe Massa ao fracasso retumbante: a saga de Luca Badoer na Ferrari",
                    "Depois de ficar dez anos sem disputar uma corrida sequer, italiano, então piloto de testes, foi chamado depois que Michael Schumacher foi vetado pelos médicos, mas teve desempenho pífio",
                    "20/08/2019",
                    "",
                    "Fred Sabino",
                    "Há dez anos, a Ferrari confirmou que Luca Badoer, então piloto de testes, seria o responsável por substituir Felipe Massa. Em recuperação do sério acidente sofrido nos treinos para o GP da Hungria, o brasileiro seguiria fora das pistas até o fim da temporada para se recuperar das lesões na testa. A notícia frustrou os fãs de Michael Schumacher, que tinha sido o escolhido pela equipe italiana mas não se mostrou apto a correr devido a dores no pescoço oriundas de um acidente de moto no começo daquele ano. Sem Massa e Schumacher, a Ferrari teve de recorrer ao veterano Badoer, que, aos 38 anos, já era integrante da equipe havia muito tempo, mas não corria desde 1999. O resultado foi não menos do que desastroso...");

            allnews[i] = n;
        }

        Fragment featured = new FeaturedFragment("Novato, Lando Norris destaca pressão de pilotar pela McLaren: \"Totalmente insana\"", "Em sua primeira temporada na principal categoria do automobilismo mundial, Norris se vê em busca de corresponder às expectativas no começo e ajudar nos ajustes do carro");
        Fragment news1 = new NewsFragment(this, "Aval do campeão", "Mercedes consulta Lewis Hamilton para decidir se mantém Valtteri Bottas na equipe em 2020");
        Fragment news2 = new NewsFragment(this, "Tensão", "Leclerc fala sobre Verstappen: \"Quando crianças, nem nos despedíamos\"");
        Fragment news3 = new NewsFragment(this, "Verstappen diz que Hamilton é dependente do carro e que há \"três ou quatro\" como ele", "Piloto holandês de 21 anos diz que há outros pilotos com a mesma capacidade do pentacampeão mundial, e Jos, pai de Max, acredita no domínio do filho na Honda");
        Fragment doublenews1 = new DoubleNewsFragment("Saudade da F1? SporTV2 apresenta domingo especial com resumo das corridas logo após a Copa Truck", "RBR sondou Fernando Alonso para o lugar de Pierre Gasly, mas espanhol recusou, diz jornal");
        Fragment news4 = new NewsFragment(this, "Rubens Barrichello confirma participação em nova categoria de fórmula criada na Austrália", "Recordista de participações na história da F1, brasileiro vai correr na S5000, com carros de 560 cavalos de potência; hoje na Stock car, piloto não corre de monoposto desde o fim de 2012");

        Fragment[] fragments = new Fragment[6];
        fragments[0] = featured;
        fragments[1] = news1;
        fragments[2] = news2;
        fragments[3] = news3;
        fragments[4] = doublenews1;
        fragments[5] = news4;

        openFragment(fragments);*/
    }

    private void onDataLoad() {
    }

    public void onResponse(String result) {
        JSONArray jarray;

        try {
            jarray = new JSONArray(result);

            for(int i=0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);

                Log.d("DBG", "USER ID: "+jobj.getString("userId"));
                Log.d("DBG", "ID: "+jobj.getString("id"));
                Log.d("DBG", "TITLE: "+jobj.getString("title"));
                Log.d("DBG", "BODY: "+jobj.getString("body"));
            }
        } catch(Exception e) {
            Log.d("DBG", e.getMessage());
        }
    }

    private BottomNavigationView mBottomNavigationView;
}
