package command;

import facade.Fachada;
import facade.IFachada;

public abstract class AbstractCommand implements ICommand{

	protected IFachada fachada = new Fachada();
}
