package test;

//import jade.core.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.StaleProxyException;
import jade.wrapper.AgentContainer;

public class Main2 {

	public static void main(String[] args) {
		Properties pp = new Properties();
		pp.setProperty(Profile.GUI, Boolean.TRUE.toString());
		Profile p = new ProfileImpl(pp);
		AgentContainer ac = Runtime.instance().createMainContainer(p);
		
		try {
			ac.acceptNewAgent("Hello", new HelloAgent()).start();
			ac.acceptNewAgent("Hi", new HelloAgent()).start();
		} catch (StaleProxyException e) {
			throw new Error(e);
		}
	}

}
