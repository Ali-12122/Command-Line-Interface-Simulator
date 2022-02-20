/*Ali Amr Nour Mokhtar         20190339
  AbdelRahman Mohammed Sobhy   20190319
  AbdelRahman AbuZaid Kisher   20190290
* */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private String commandName;
    private ArrayList<String> args = new ArrayList<>();


    public void parse(String input) {
        if (input.contains(" ")) {
            args = new ArrayList<>();
                Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
                while (m.find())
                    args.add(m.group(1).replace("\"",""));
                commandName = args.get(0);
                args.remove(0);
        }else{
            this.commandName = input;
            this.args = null;
        }
    }


    public String getCommandName() {
        return this.commandName;
    }

    public ArrayList<String> getArguments() {
        return this.args;
    }
}
