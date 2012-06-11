package com.android.diabetescontrol.principal;

import java.sql.Timestamp;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.diabetescontrol.activities.CadastroMedicoDoPaciente;
import com.android.diabetescontrol.activities.CadastroPacienteActivity;
import com.android.diabetescontrol.activities.CadastroRegistroActivity;
import com.android.diabetescontrol.activities.ConfiguracoesActivity;
import com.android.diabetescontrol.activities.ListaEditarRegistrosActivity;
import com.android.diabetescontrol.activities.ListaNotasRegistrosMedicosActivity;
import com.android.diabetescontrol.activities.ListaPacientesActivity;
import com.android.diabetescontrol.activities.PreferenciasActivity;
import com.android.diabetescontrol.activities.R;
import com.android.diabetescontrol.activities.RelatoriosActivity;
import com.android.diabetescontrol.activities.SelectGraficosActivity;
import com.android.diabetescontrol.business.GlicoseMediaBusiness;
import com.android.diabetescontrol.database.ContextoDados;
import com.android.diabetescontrol.database.PacienteDAO;
import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Medicamento;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Utils;

public class PrincipalActivity extends Activity {
	private Button btAdicionar;
	private Button btEditar;
	private Button btGraficos;
	private Button btConfiguracoes;
	private Button btRelatoriosPacientes;
	private Button btCadastrarPaciente;
	private Button btRelatorios;
	private Button btAdicionarPaciente;
	private Button btGraficosPaciente;
	private Button btNotasMedicas;
	private TextView tvValorHoje;
	private TextView tvValorOntem;
	private TextView tvValorSemana;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new ContextoDados(this);
		// insereTestes(this);
		Medicamento.preencheListaMedicamento(this);
		if (Utils.isPaciente(this)) {
			setContentView(R.layout.main_paciente);
			inicializaObjetosPaciente();
			carregaListenersPaciente();
			carregaResumo();
			carregaCodPaciente();
		} else {
			setContentView(R.layout.main_medico);
			inicializaObjetosMedico();
			carregaListenersMedico();
		}
	}

	private void carregaCodPaciente() {
		PacienteDAO pacDao = new PacienteDAO(this);
		pacDao.open();
		Paciente.setCODIGOPACIENTE(pacDao.getCodPac());
		pacDao.close();
	}

	private void insereTestes(Context ctx) {
		PacienteDAO pacDao = new PacienteDAO(ctx);
		pacDao.open();
		pacDao.criarPaciente(new Paciente(null, "Leonardo Alves",
				"leo.alvesneuwald@gmail.com", null, "M", "leoneuwald", null));
		pacDao.criarPaciente(new Paciente(null, "João Silva",
				"joao.silva@gmail.com", null, "M", "joaosilva", null));
		pacDao.criarPaciente(new Paciente(null, "Marcos Antonio",
				"marcos.antonio@bol.com", null, "M", "marcos", null));
		pacDao.criarPaciente(new Paciente(null, "Maria Silva",
				"maria.silva@gmail.com", null, "F", "maria", null));
		pacDao.close();
		RegistroDAO regDao = new RegistroDAO(ctx);
		regDao.open();
		regDao.criarRegistro(new Registro(null, "Glicose", "Antes do Café",
				12.2f, new Timestamp(new Date().getTime()), "S", "leoneuwald",
				"mmol/l", 1, "M", null, null));
		regDao.criarRegistro(new Registro(null, "Glicose", "Depois do Café",
				14.2f, new Timestamp(new Date().getTime()), "S", "leoneuwald",
				"mmol/l", 1, "M", null, null));
		regDao.criarRegistro(new Registro(null, "HbA1c", "Antes do Café",
				10.2f, new Timestamp(new Date().getTime()), "S", "leoneuwald",
				"%", 1, "M", null, null));
		regDao.criarRegistro(new Registro(null, "HbA1c", "Antes do Café",
				10.2f, new Timestamp(new Date().getTime()), "S", "leoneuwald",
				"%", 1, "M", null, null));
		regDao.criarRegistro(new Registro(null, "Peso", "Antes do Café", 80.2f,
				new Timestamp(new Date().getTime()), "S", "leoneuwald", "kg",
				1, "M", null, null));
		regDao.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.layout.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.configura) {
			Intent i = new Intent(PrincipalActivity.this,
					PreferenciasActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	private void carregaListenersMedico() {
		btConfiguracoes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						ConfiguracoesActivity.class);
				startActivity(i);

			}
		});
		btAdicionarPaciente.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						CadastroMedicoDoPaciente.class);
				startActivity(i);
			}
		});
		btRelatoriosPacientes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						ListaPacientesActivity.class);
				startActivity(i);
			}
		});
		btNotasMedicas.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						ListaNotasRegistrosMedicosActivity.class);
				startActivity(i);
			}
		});
		btGraficosPaciente.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						ListaPacientesActivity.class);
				i.putExtra("origem", "G");
				startActivity(i);
			}
		});

	}

	private void inicializaObjetosMedico() {
		btConfiguracoes = (Button) findViewById(R.id.btConfiguracoes);
		btAdicionarPaciente = (Button) findViewById(R.id.btAddPaciente);
		btRelatoriosPacientes = (Button) findViewById(R.id.btRelatoriosPacientes);
		btNotasMedicas = (Button) findViewById(R.id.btNotasMedicas);
		btGraficosPaciente = (Button) findViewById(R.id.btGraficosPaciente);
	}

	private void inicializaObjetosPaciente() {
		btConfiguracoes = (Button) findViewById(R.id.btConfiguracoes);
		btAdicionar = (Button) findViewById(R.id.btAdicionar);
		btRelatorios = (Button) findViewById(R.id.btRelatorios);
		btEditar = (Button) findViewById(R.id.btEditar);
		btGraficos = (Button) findViewById(R.id.btGraficos);
		tvValorHoje = (TextView) findViewById(R.id.etvUltimoReg);
		tvValorOntem = (TextView) findViewById(R.id.etvOntem);
		tvValorSemana = (TextView) findViewById(R.id.etvSemana);
		btCadastrarPaciente = (Button) findViewById(R.id.btCadastrarPaciente);
	}

	private void carregaListenersPaciente() {
		btAdicionar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						CadastroRegistroActivity.class);
				startActivity(i);
			}
		});
		btCadastrarPaciente.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						CadastroPacienteActivity.class);
				startActivity(i);
			}
		});
		btEditar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						ListaEditarRegistrosActivity.class);
				startActivity(i);
			}
		});
		btGraficos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						SelectGraficosActivity.class);
				startActivity(i);
			}
		});
		btConfiguracoes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						ConfiguracoesActivity.class);
				startActivity(i);

			}
		});
		btRelatorios.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						RelatoriosActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	private void carregaResumo() {
		GlicoseMediaBusiness gmb = new GlicoseMediaBusiness();
		gmb.getValorMedio(new RegistroDAO(this));
		tvValorHoje.setText(gmb.getValorMedioHoje());
		tvValorOntem.setText(gmb.getValorMedioOntem());
		tvValorSemana.setText(gmb.getValorMedioSemana());
	}

	@Override
	protected void onResume() {
		super.onResume();
		// A activity tornou-se visível.
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Outra activity tem o foco na aplicação.
	}

	@Override
	protected void onStop() {
		super.onStop();
		// A activity não é mais visível.
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// A activity foi destruída.
	}

}