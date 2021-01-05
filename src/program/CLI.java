package program;

import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Record changes to the repository")
class CommandCommit {
	@Parameter(names = "-m", description = "add message for each commit", required = false)
	private String message;

	void run() {
		if (message == null) {
			new Commit();
		} else {
			new Commit(message);
		}
	}
}

@Parameters(separators = "=", commandDescription = "Create branch and show branch information")
class CommandBranch {
	@Parameter(names = { "-n", "--new" }, description = "create new branch with branch name")
	private String branchName;
	@Parameter(names = { "-s", "--switch" }, description = "create and switch to the new branch")
	private boolean isSwitch;
	@Parameter(names = { "-a", "--all" }, description = "show all the branches")
	private boolean showAll;
	@Parameter(names = { "-c", "--current" }, description = "show the current branch")
	private boolean showCurrent;

	void run() {
		if (branchName != null) {
			new Branch(branchName, isSwitch);
		}
		if (showAll) {
			BranchControl.printAllBranches();
		}
		if (showCurrent) {
			System.out.println(BranchControl.getCurrentBranch());
		}

	}
}

@Parameters(separators = "=", commandDescription = "Switch between branches")
class CommandSwitch {
	@Parameter(description = "branch name", required = true)
	private String branchName;

	void run() {
		BranchControl.switchBranch(branchName);
	}
}

@Parameters(commandDescription = "Rename branch")
class CommandRename {
	@Parameter(description = "rename branch", arity = 2, required = true)
	private List<String> branchNames;

	void run() {
		BranchControl.renameBranch(branchNames.get(0), branchNames.get(1));
	}
}

@Parameters(separators = "=", commandDescription = "Reset commit to history")
class CommandReset {
	@Parameter(names = "-HARD", description = "choose the reset mode", order = 0)
	private boolean isHard;
	@Parameter(names = { "-t", "--times" }, description = "reset to the specific times of commit history")
	private int times = 0;
	@Parameter(names = { "-c", "--commit" }, description = "reset to the specific commit history")
	private String commitKey;

	void run() throws Exception {
		if ((times != 0 && commitKey != null) || times == 0 && commitKey == null) {
			System.out.println("输入错误！");
		}
		if (isHard) {
			if (times != 0) {
				Reset.resetHard(times);
			}
			if (commitKey != null) {
				Reset.resetHard(commitKey);
			}
		} else {
			if (times != 0) {
				Reset.resetMixed(times);
			}
			if (commitKey != null) {
				Reset.resetMixed(commitKey);
			}
		}
	}
}

@Parameters(separators = "=", commandDescription = "Read logs")
class CommandLog {
	@Parameter(names = { "-c", "--current" }, description = "read the log of current branch")
	private boolean current;
	@Parameter(names = { "-a", "--all" }, description = "read the log of all branches")
	private boolean all;

	void run() {
		if (current) {
			BranchControl.getCurrentBranchLog();
		}
		if (all) {
			BranchControl.getAllLog();
		}
	}
}

public class CLI {
	@Parameter(names = "--init", description = "initialize the repository")
	private boolean init;

	@Parameter(names = "--help", description = "read help document")
	private boolean help;

	public static void main(String... argv) throws Exception {
		System.setProperty("user.dir", "E:\\JavaWorkspace\\testdir2");
		CLI cli = new CLI();
		CommandCommit commit = new CommandCommit();
		CommandBranch branch = new CommandBranch();
		CommandSwitch cswitch = new CommandSwitch();
		CommandRename rename = new CommandRename();
		CommandReset reset = new CommandReset();
		CommandLog log = new CommandLog();
		JCommander commander = JCommander.newBuilder().programName("Version Control System").addObject(cli)
				.addCommand("commit", commit).addCommand("branch", branch).addCommand("switch", cswitch)
				.addCommand("rename", rename).addCommand("reset", reset).addCommand("log", log).build();
		try {
			commander.parse(argv);
		} catch (ParameterException pe) {
			System.out.println("输入参数不正确，请重新输入。ParameterException");
			return;
		}
//		commander.parse("--help");
		if (cli.help) {
			commander.usage();
			return;
		}
		if (cli.init) {
			FilepathSetting.InitRepo();
			new Branch();
		}
		if (!FilepathSetting.isInitialized()) {
			System.out.println("请先初始化仓库！");
			return;
		}
		try {
			if (commander.getParsedCommand().equals("commit")) {
				commit.run();
			}
			if (commander.getParsedCommand().equals("branch")) {
				branch.run();
			}
			if (commander.getParsedCommand().equals("switch")) {
				cswitch.run();
			}
			if (commander.getParsedCommand().equals("rename")) {
				rename.run();
			}
			if (commander.getParsedCommand().equals("reset")) {
				reset.run();
			}
			if (commander.getParsedCommand().equals("log")) {
				log.run();
			}
		} catch (NullPointerException e) {
//			System.out.println("NullPointerException");
		}
	}
}