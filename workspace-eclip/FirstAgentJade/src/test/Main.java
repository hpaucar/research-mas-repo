package test;
import jade.core.Agent;
import jade.core.ProfileImpl;

public class Main {

	
		public static void main(String[] args){
		    Runtime rt = Runtime.instance();

		    // 1) create a platform (main container+DF+AMS)
		    Profile pMain = new ProfileImpl(PLATFORM_IP, PLATFORM_PORT, PLATFORM_ID);
		    System.out.println("Launching a main-container..."+pMain);
		    AgentContainer mainContainerRef = rt.createMainContainer(pMain); //DF and AMS are include

		    // 2) (optionnal) create one or several containers
		    String containerName;
		    ProfileImpl pContainer;
		    System.out.println("Launching containers ...");

		    containerName="Mycontainer1";
		    pContainer = new ProfileImpl(PLATFORM_IP, PLATFORM_PORT, PLATFORM_ID);
		    pContainer.setParameter(Profile.CONTAINER_NAME,containerName);
		    System.out.println("Launching container "+pContainer);
		    containerRef = rt.createAgentContainer(pContainer); 

		    // 3) (optionnal) create monitoring agents : rma agent, used to debug and monitor the platform; sniffer agent, to monitor communications; 
		    createMonitoringAgents(mainContainerRef);

		    System.out.println("Plaform ok");

		    //4 ) (optionnal) Create and start your agents
		    createMyAgent(containerWhereToCreateMyAgent);
		}
	}

}
