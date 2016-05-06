/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.voxtab.ariatech.voxtab;



import android.content.Context;
import android.content.Intent;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

    /**
     */
    static final String SERVER_URL = "";

    /**
     * Google API project id registered to use GCM.
     */
   public static final String SENDER_ID ="1087092221784";


	  
   //Ariatech Id
   //1087092221784	AIzaSyADUynDZqER4Go8bPh4cRyewrfQnR2c6S4
   
   //RAI ID
   //263281428774   AIzaSyDM_92Qe5avz0BTEcOOjw_d0z6oTx201EY
    /**
     * Tag used on log messages.
     *
     *
     */

   // Id: APA91bGFNLBlZ50vbyWBMLJuPMSphsGAn0urSbbRcxPaitwqbS1EuevBpg2xXD-uQWg0oy0gp0Mqzp4vi2Dmvj5lxyqhcL-D3TQ69aSRfTbMrjP2psZRkdBNL4AKvVSr_TKOdw2mb77A

    static final String TAG = "Voxtab ";

    /**
     * Intent used to display a message in the screen.
     */
    static final String DISPLAY_MESSAGE_ACTION =
            "com.voxtab.ariatech.voxtab.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
   public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
//        context.sendBroadcast(intent);
        GlobalData.printMessage(message);
    }
}
