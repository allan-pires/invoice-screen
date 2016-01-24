package com.nubank.allan.billscreen.controller;

import android.os.AsyncTask;

import com.nubank.allan.billscreen.controller.task.RESTTask;
import com.nubank.allan.billscreen.controller.task.TaskResult;

import junit.framework.TestCase;

import org.mockito.Mockito;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by doisl_000 on 1/24/2016.
 */
public class RESTTaskTest extends TestCase {
    private URL params;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        params = new URL("https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json");
    }

    public void testExecute_returnsTaskResult_whenCalled() throws MalformedURLException, ExecutionException, InterruptedException {
        RESTTask task = new RESTTask();
        String jsonString = "[\n" +
                "  {\n" +
                "    \"bill\": {\n" +
                "      \"state\": \"overdue\",\n" +
                "      \"id\": \"55256cd0-10f7-4fd3-a3be-21" +
                "3bfe01857d\",\n" +
                "      \"summary\": {\n" +
                "        \"due_date\": \"2015-04-20\",\n" +
                "        \"close_date\": \"2015-04-07\",\n" +
                "        \"past_balance\": 0,\n" +
                "        \"total_balance\": 38933,\n" +
                "        \"interest\": 0,\n" +
                "        \"total_cumulative\": 38933,\n" +
                "        \"paid\": 38933,\n" +
                "        \"minimum_payment\": 5840,\n" +
                "        \"open_date\": \"2015-03-17\"\n" +
                "      },\n" +
                "      \"_links\": {\n" +
                "        \"self\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d\"\n" +
                "        },\n" +
                "        \"boleto_email\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d/boleto/email\"\n" +
                "        },\n" +
                "        \"barcode\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d/boleto/barcode\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"barcode\": \"03394643200000389339646532300000001745800102\",\n" +
                "      \"linha_digitavel\": \"03399.64652  32300.000000  17458.001025  4  64320000038933\",\n" +
                "      \"line_items\": [\n" +
                "        {\n" +
                "          \"post_date\": \"2015-03-31\",\n" +
                "          \"amount\": 3850,\n" +
                "          \"title\": \"Rest Bolinha\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/551585a8-244c-4ab8-9dce-0113d465ad8e\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-03-31\",\n" +
                "          \"amount\": 12505,\n" +
                "          \"title\": \"Posto de Servico Dinam\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55152410-5315-4aeb-b7f3-263170767191\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-02\",\n" +
                "          \"amount\": 4180,\n" +
                "          \"title\": \"Brunetto Massa e Risot\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/551ac6d1-3c44-4f68-a404-788083f00955\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-07\",\n" +
                "          \"amount\": 18398,\n" +
                "          \"title\": \"Pao de Acucar Lj\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55219aaa-2b9d-40bc-a8d3-bb31f14d2382\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"bill\": {\n" +
                "      \"state\": \"closed\",\n" +
                "      \"id\": \"554cc619-5998-4687-8fd3-743fd88ef704\",\n" +
                "      \"summary\": {\n" +
                "        \"due_date\": \"2015-05-20\",\n" +
                "        \"close_date\": \"2015-05-07\",\n" +
                "        \"past_balance\": 0,\n" +
                "        \"total_balance\": 174326,\n" +
                "        \"interest\": 0,\n" +
                "        \"total_cumulative\": 174326,\n" +
                "        \"paid\": 0,\n" +
                "        \"minimum_payment\": 26149,\n" +
                "        \"open_date\": \"2015-04-07\"\n" +
                "      },\n" +
                "      \"_links\": {\n" +
                "        \"self\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/bills/554cc619-5998-4687-8fd3-743fd88ef704\"\n" +
                "        },\n" +
                "        \"boleto_email\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/bills/554cc619-5998-4687-8fd3-743fd88ef704/boleto/email\"\n" +
                "        },\n" +
                "        \"barcode\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/bills/554cc619-5998-4687-8fd3-743fd88ef704/boleto/barcode\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"barcode\": \"03397643400001743269646532300000001745800102\",\n" +
                "      \"linha_digitavel\": \"03399.64652  32300.000000  17458.001025  7  64340000174326\",\n" +
                "      \"line_items\": [\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-08\",\n" +
                "          \"amount\": -38933,\n" +
                "          \"title\": \"Pagamento recebido\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-11\",\n" +
                "          \"amount\": 17270,\n" +
                "          \"title\": \"Eolo Restaurante e Caf\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5526abd4-7439-4749-a39b-1ac6b1914d60\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-14\",\n" +
                "          \"amount\": 11162,\n" +
                "          \"title\": \"Centreville Serv\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/552b044e-acd3-4fc7-be52-07ff1624aa1e\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-14\",\n" +
                "          \"amount\": 4299,\n" +
                "          \"title\": \"Coisa Boa\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5527ffc3-ce31-4797-8edf-112dfebb30df\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-14\",\n" +
                "          \"amount\": 8200,\n" +
                "          \"title\": \"Purpurina Of de Pizzas\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5528642e-324a-4518-a970-261d2dd7f8be\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-15\",\n" +
                "          \"amount\": 10856,\n" +
                "          \"title\": \"la da Venda\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/552ad24a-ee6d-41ae-9163-1d5bc05ad05d\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-18\",\n" +
                "          \"amount\": 1500,\n" +
                "          \"title\": \"Stop Green\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55302f74-34f7-4dfa-927a-623c1b8de677\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-18\",\n" +
                "          \"amount\": 6674,\n" +
                "          \"title\": \"Supermercados Mambo\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/553037ae-a115-46dc-97cc-cb61385575da\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-21\",\n" +
                "          \"amount\": 858,\n" +
                "          \"title\": \"Schoenberger Sorv\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55341952-3916-47df-8fad-7bce92626a97\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-21\",\n" +
                "          \"amount\": 1274,\n" +
                "          \"title\": \"Schoenberger Sorv\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55342ed2-0402-491d-8d46-13fd62953be8\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-21\",\n" +
                "          \"amount\": 5306,\n" +
                "          \"title\": \"Auto Posto Playgas\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5532c42f-5d22-426a-94f9-15ac615fc367\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-23\",\n" +
                "          \"amount\": 21356,\n" +
                "          \"title\": \"Zaffari Lj 25 Bourbon\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5536b428-e638-4c50-9413-0b45189bcf42\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-23\",\n" +
                "          \"amount\": 11630,\n" +
                "          \"title\": \"America Bourbon\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55369f68-0230-4c03-b210-e8bbc0e49135\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-24\",\n" +
                "          \"amount\": 11088,\n" +
                "          \"title\": \"Wall Street Bar\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5538554b-8379-435d-9e0f-3d947b7958ef\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-28\",\n" +
                "          \"amount\": 12601,\n" +
                "          \"title\": \"Auto Posto Cerro Cora\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/553d2f73-8871-4fc0-9d89-ae2bc6de5cfd\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-28\",\n" +
                "          \"amount\": 7333,\n" +
                "          \"title\": \"Pao de Acucar Lj\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/553d2d14-9da9-41a1-8882-ff7533a0a6ed\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-04-29\",\n" +
                "          \"amount\": 7606,\n" +
                "          \"title\": \"Esso Auto Posto\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55355574-3670-4c19-aca3-48fc364992f0\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-05\",\n" +
                "          \"amount\": 12169,\n" +
                "          \"title\": \"Casa do Espeto Pompéia\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5545189a-f16d-4938-8bd2-cb9a197900fa\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-05\",\n" +
                "          \"amount\": 8056,\n" +
                "          \"title\": \"Supermercados Mambo\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5542b9b0-d502-4022-9470-887d586d3076\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-05\",\n" +
                "          \"amount\": 2400,\n" +
                "          \"title\": \"Ta Kombinado Pasta\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/554248d7-9fc0-4c18-8a35-6d20239f8cd3\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-06\",\n" +
                "          \"amount\": 6180,\n" +
                "          \"title\": \"Hellofood Intermediaca Sao Paulo Bra\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5546a598-7d1d-42c8-878c-965f35a0fa9b\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-06\",\n" +
                "          \"amount\": 4758,\n" +
                "          \"title\": \"de Guste Cafe Ltda Sao Paulo Bra\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/554a410c-ecbc-4469-93b5-96edf1657f41\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-07\",\n" +
                "          \"amount\": 1750,\n" +
                "          \"title\": \"Bar Lanches Galhardo\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5548f7de-be3e-4a42-977a-25fce7d07bab\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"bill\": {\n" +
                "      \"state\": \"open\",\n" +
                "      \"summary\": {\n" +
                "        \"minimum_payment\": 5944,\n" +
                "        \"total_balance\": 39627,\n" +
                "        \"interest\": 0,\n" +
                "        \"due_date\": \"2015-06-20\",\n" +
                "        \"close_date\": \"2015-06-05\",\n" +
                "        \"total_cumulative\": 39627,\n" +
                "        \"open_date\": \"2015-05-07\",\n" +
                "        \"past_balance\": 0\n" +
                "      },\n" +
                "      \"_links\": {\n" +
                "        \"barcode\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/accounts/55085752-3dcf-42a5-870a-81eed6b08172/bills/open/boleto/barcode\"\n" +
                "        },\n" +
                "        \"boleto_email\": {\n" +
                "          \"href\": \"https://prod-accounts.nubank.com.br/api/accounts/55085752-3dcf-42a5-870a-81eed6b08172/bills/open/boleto/email\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"barcode\": \"03395646500000396279646532300000001745800102\",\n" +
                "      \"linha_digitavel\": \"03399.64652  32300.000000  17458.001025  5  64650000039627\",\n" +
                "      \"line_items\": [\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-09\",\n" +
                "          \"amount\": 1642,\n" +
                "          \"title\": \"Al Fanous Restaurante\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/554b8b66-0f3b-444b-9f67-2f467bd2e032\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-12\",\n" +
                "          \"amount\": 18402,\n" +
                "          \"title\": \"Bem Brasil Grelhados\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/554d6515-669f-4214-8cab-d67113deebdb\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-15\",\n" +
                "          \"amount\": 8901,\n" +
                "          \"title\": \"Babbo Giovanni\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55568d22-998d-47a0-a4b8-315e21b3cbda\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-15\",\n" +
                "          \"amount\": 6217,\n" +
                "          \"title\": \"Apl* Itunes.com/bill\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/555653bc-9070-49bc-ab4d-c7556db1ff34\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-15\",\n" +
                "          \"amount\": 311,\n" +
                "          \"title\": \"Apl* Itunes.com/bill\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/55564ec1-aeb8-44cd-87d8-b1f434c03148\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-15\",\n" +
                "          \"amount\": 2200,\n" +
                "          \"title\": \"Vitrollasfoodtruck\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 1,\n" +
                "          \"href\": \"nuapp://transaction/5556120b-d490-4ff1-b644-2a8732a02192\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"post_date\": \"2015-05-15\",\n" +
                "          \"amount\": 1954,\n" +
                "          \"title\": \"Gol Tran Aereo Interne\",\n" +
                "          \"index\": 0,\n" +
                "          \"charges\": 2,\n" +
                "          \"href\": \"nuapp://transaction/55540206-0099-4c01-9209-81f298dc9b93\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"bill\": {\n" +
                "      \"state\": \"future\",\n" +
                "      \"summary\": {\n" +
                "        \"minimum_payment\": 293,\n" +
                "        \"total_balance\": 1954,\n" +
                "        \"interest\": 0,\n" +
                "        \"due_date\": \"2015-07-20\",\n" +
                "        \"close_date\": \"2015-07-07\",\n" +
                "        \"total_cumulative\": 1954,\n" +
                "        \"open_date\": \"2015-06-05\",\n" +
                "        \"past_balance\": 0\n" +
                "      },\n" +
                "      \"_links\": {\n" +
                "        \n" +
                "      },\n" +
                "      \"line_items\": [\n" +
                "        {\n" +
                "          \"post_date\": \"2015-06-15\",\n" +
                "          \"amount\": 1954,\n" +
                "          \"title\": \"Gol Tran Aereo Interne\",\n" +
                "          \"index\": 1,\n" +
                "          \"charges\": 2,\n" +
                "          \"href\": \"nuapp://transaction/55540206-0099-4c01-9209-81f298dc9b93\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "]\n";

        TaskResult expected = new TaskResult(jsonString, 200);
        TaskResult result = task.execute(params).get();

        assertEquals(expected.getCode(), result.getCode());
        assertEquals(expected.getResult(), result.getResult());
    }

    public void testExecute_returnsErrorCode_whenNullURL() throws ExecutionException, InterruptedException {
        RESTTask task = new RESTTask();

        TaskResult expected = new TaskResult("", 500);
        TaskResult result = task.execute((URL) null).get();

        assertEquals(expected.getCode(), result.getCode());
        assertEquals(expected.getResult(), result.getResult());
    }
}
