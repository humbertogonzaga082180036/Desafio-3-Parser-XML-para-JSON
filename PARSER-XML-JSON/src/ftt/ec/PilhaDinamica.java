package ftt.ec;

public class PilhaDinamica {
	private Estado topo = null;
    private int quantidade;

    public int Tamanho()
    {
        return quantidade;
    }
            
    public void empilha(String valor)
    {
        Estado novoEstado = new Estado();
        novoEstado.setValor(valor);
        novoEstado.setAnterior(topo);
        topo = novoEstado;
        quantidade++;
    }

    public String desempilha()
    {
        if (Tamanho() == 0)
        {
        	return "";
        }
        else
        {
            String retorno = topo.getValor();
            topo = topo.getAnterior();
            quantidade--;
            return retorno;
        }
    }

    public Estado retornaTopo()
    {
        if (Tamanho() == 0)
            return null;
        else
            return topo;
    }
}
