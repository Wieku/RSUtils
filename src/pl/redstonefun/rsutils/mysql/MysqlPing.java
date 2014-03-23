package pl.redstonefun.rsutils.mysql;

public class MysqlPing implements Runnable {

	@Override
	public void run()
	{
		ConnectionGetter.getInstance().ping();
	}

}
