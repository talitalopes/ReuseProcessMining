package br.ufrj.cos.prisma.miner.Extractor;

import minerv1.FrameworkProcess;

import org.eclipse.swt.widgets.Shell;

import br.ufrj.cos.prisma.miner.util.Constants;
import br.ufrj.cos.prisma.miner.util.Log;

public class RepositoriesExtractor {

	public static void start(FrameworkProcess process, Shell shell) {
		if (process == null) {
			Log.i(Constants.ERROR_KEY, Constants.PROCESS_NOT_EXISTS);
			return;
		}

		if (process.getName() == null) {
			Log.i(Constants.ERROR_KEY, Constants.ERROR_LOADING_FRAMEWORK);
			return;
		}

		System.out.println(String.format(
				"Extracting reuse actions related to %s framework",
				process.getName()));

	}
	
}
