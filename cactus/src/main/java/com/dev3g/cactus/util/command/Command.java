package com.dev3g.cactus.util.command;

public interface Command {

	void setReceiver(Receiver receiver);

	void execute() throws Exception;
}
