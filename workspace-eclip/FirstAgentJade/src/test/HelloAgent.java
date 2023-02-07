package test;
import jade.core.Agent;

  public class HelloAgent extends Agent
  {
      @Override
	protected void setup()
      {
          System.out.println("Hello World. ");
          System.out.println("My name is "+ getLocalName());
      }
  }
