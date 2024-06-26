package org.molgenis.asterix.pipeline;

import org.apache.commons.cli.ParseException;
import org.molgenis.asterix.config.ConfigProvider;

import java.io.IOException;

/**
 * class that is the application entrypoint
 *
 * @author OelenR
 */
public class AppStarter {

    /**
     * application entry
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppStarter app = new AppStarter();
        app.start(args);
    }

    /*
    example args:
    java -jar asterix.jar -star_out C:\\molgenis\\asterix_data\\star_alleles\\
        -snp_haplo_table_dir C:\\molgenis\\asterix_data\\snp_to_haplo\\
        -haplo_type_dir C:\\molgenis\\asterix_data\\ll_phased_active\\
        -haplo_pheno_table_dir C:\\molgenis\\asterix_data\\haplo_to_pheno\\
        -pheno_out_dir C:\\molgenis\\asterix_data\\predicted_phenotypes\\
        -sample_matrix_out C:\\molgenis\\asterix_data\\sample_matrix.csv
     */

    /**
     * start the application
     * @param args the command line arguments
     */
    public void start(String[] args) {
        try {
            //load config
            ConfigProvider configProvider = ConfigProvider.getInstance();
            configProvider.loadCliArguments(args);
            //check for help request by user
            if(!configProvider.isRequestedHelp()) {
                Pipeline pipeline = new Pipeline();
                pipeline.doPipeline();
            }
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
