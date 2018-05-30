package Modelo;

import java.util.List;

import pojos.Horas;

public interface HorasInterface {
	public Horas getHorasByRango(String hora);
	public List<Horas> getAllHoras();
}
