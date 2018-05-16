package com.sudo.v3.base;

import com.runemate.game.api.hybrid.util.io.ManagedProperties;
import com.sudo.v3.antiban.BreakHandler;
import com.sudo.v3.interfaces.IAntiBan;
import com.sudo.v3.interfaces.ISudoBot;
import com.sudo.v3.antiban.AntiBanHandler;
import com.sudo.v3.spectre.bots.apexcrabber.threads.TrialWorker;
import com.sudo.v3.spectre.common.SudoTimer;
import com.sudo.v3.spectre.common.leafs.*;
import com.sudo.v3.spectre.common.playersense.ApexPlayerSense;
import com.sudo.v3.spectre.common.playersense.inventory.FastClick;
import com.sudo.v3.spectre.statics.UserList;
import com.sudo.v3.spectre.tasker.TaskHandler;
import com.sudo.v3.ui.Info;
import com.sudo.v3.spectre.statics.UpdateUI;
import com.sudo.v3.ui.model.XPInfo;
import com.sudo.v3.spectre.common.navigation.Navigator;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.GameEvents;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.net.GrandExchange;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.osrs.net.OSBuddyExchange;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.tree.TreeBot;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SudoPro on 11/23/2016.
 */
public abstract class SudoBot extends TreeBot implements ISudoBot, EmbeddableUI, InventoryListener
{
    public Navigator nav = new Navigator();

    public boolean guiWait = true, useAntiBan = false, useMiddleMouseCamera = false, checkAnagogic = true, enableRun = true, isRS3 = true, firstLogin = true, breakEnabled = true, currentlyBreaking = false;
    public boolean useSpecial = false;
    //public boolean isDropping = false;
    public String[] itemsToDrop;

    public Player player;
    public int grossIncome = 0, runEnergy = 25, stopAfterMinutes = 0;

    public BreakHandler breakHandler = new BreakHandler(this);

    public AntiBanHandler abHandler = new AntiBanHandler(this);
    public ArrayList<IAntiBan> antibanList = new ArrayList<IAntiBan>();

    public final StopWatch STOPWATCH = new StopWatch();
    public SudoTimer anagogicOrtTimer = new SudoTimer(5000, 30000),
            checkSpecialTimer = new SudoTimer(45000, 210000);

    public SimpleObjectProperty<Node> botInterfaceProperty;

    public LinkedHashMap<Skill, XPInfo> XPInfoMap = new LinkedHashMap<>();
    public LinkedHashMap<String, String> displayInfoMap = new LinkedHashMap<>();

    private HashMap<Integer, Integer> acquiredIDs = new HashMap<>();

    public String currentTaskString, abTaskString;

    public ArrayList<Node> guiList;
    //public MouseOverlay mouseOverlay;

    public ManagedProperties managedProperties;
    public long trialRunTime = 0;

    // Create regex trialPattern
    // value should be in the format of "Date - Milliseconds Botted"
    // Exampled "05/24/2017 - 253221"
    public Pattern trialPattern = Pattern.compile("(?i)^(.*)\\s-\\s(.*)");

    public Info info = new Info(null , null, " ", " ", " ");

    public TaskHandler taskHandler = new TaskHandler();

    // Leafs
    public EmptyLeaf emptyLeaf = new EmptyLeaf(this);
    public LiteVersionExpireLeaf liteVersionExpireLeaf = new LiteVersionExpireLeaf(this);
    public DeselectInventoryItemLeaf deselectInventoryItemLeaf = new DeselectInventoryItemLeaf(this);
    public OpenBankLeaf openBankLeaf = new OpenBankLeaf(this);
    public CloseBankLeaf closeBankLeaf = new CloseBankLeaf(this);
    public CloseDepositBoxLeaf closeDepositBoxLeaf = new CloseDepositBoxLeaf(this);
    public ShiftDropLeaf shiftDropLeaf = new ShiftDropLeaf(this);
    public DepositAllLeaf depositAllLeaf = new DepositAllLeaf(this);
    public UseSpecialLeaf useSpecialLeaf = new UseSpecialLeaf(this);

    private TrialWorker trialWorker;
    public FastClick fastClick;

    @Override
    public void onStart(String... args) {

        managedProperties = getSettings();

        // Initialize PlayerSense Keys
        ApexPlayerSense.initializeKeys();

        // Start new TrialWorker thread
        trialWorker = new TrialWorker(this);
        trialWorker.start();

        fastClick = new FastClick(this);

        if(Environment.isRS3()) {
            isRS3 = true;
            //GameEvents.RS3.UNEXPECTED_ITEM_HANDLER.disable();
        }
        else
            isRS3 = false;


        anagogicOrtTimer.start();

        // Sets the length of time in milliseconds to wait before calling onLoop again
        setLoopDelay(250, 750);

        // Start the stopwatch
        STOPWATCH.start();
    }

    @Override
    public void onItemAdded(ItemEvent event) {
        ItemDefinition definition = event.getItem().getDefinition();
        if (definition != null) {
            if(!acquiredIDs.containsKey(definition.getId())){
                try {
                    if(Environment.isRS3())
                        acquiredIDs.put(definition.getId(), GrandExchange.lookup(definition.getId()).getPrice());
                    else
                        acquiredIDs.put(definition.getId(), OSBuddyExchange.getGuidePrice(definition.getId()).getSelling());
                }catch(Exception e){
                    acquiredIDs.put(definition.getId(), 0);
                }
            }

            for(int i = 0; i < event.getQuantityChange(); i++)
                grossIncome += acquiredIDs.get(definition.getId());
        }
    }

    @Override
    public void onItemRemoved(ItemEvent event){
        ItemDefinition definition = event.getItem().getDefinition();
        if (definition != null) {
            if(!acquiredIDs.containsKey(definition.getId())){
                try {
                    if(Environment.isRS3())
                        acquiredIDs.put(definition.getId(), GrandExchange.lookup(definition.getId()).getPrice());
                    else
                        acquiredIDs.put(definition.getId(), OSBuddyExchange.getGuidePrice(definition.getId()).getSelling());
                }catch(Exception e){
                    acquiredIDs.put(definition.getId(), 0);
                }
            }
            for(int i = 0; i < event.getQuantityChange(); i++)
                grossIncome -= acquiredIDs.get(definition.getId());
        }
    }

    public void changeProperty(int index){
        if(guiList != null) {
            if (index < guiList.size())
                UpdateUI.debug("Setting EmbeddableUI to Index: " + index);
            botInterfaceProperty.set(guiList.get(index));
        }
    }

    public void setCurrentTask(String foo){
        currentTaskString = foo;
        updateInfo();
    }

    public void setAntiBanTask(String foo){
        abTaskString = foo;
        updateInfo();
    }

    public boolean isPrivate(){
        return false;
    }

    public boolean isValidSession(){
        return STOPWATCH.getRuntime(TimeUnit.MILLISECONDS) <= 900000 || isPrivate() || UserList.whiteList.contains(Environment.getForumName());
    }

    public boolean isValidSession(String key) {

        String localDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String value;

        // If the key exists
        if (managedProperties.containsKey(key)) {
            value = managedProperties.getProperty(key);
            UpdateUI.debug("Key found, value is: " + value);
        }
        else {
            UpdateUI.debug("Key not found");
            value = null;
        }

        if (value != null) {
            // Using trialPattern initialized on startup

            // Grab Matches from our current value
            Matcher matcher = trialPattern.matcher(value);

            // If any matches exist
            if (matcher.find()) {

                // If the Trial Date is the same as the Local Date, then the botter is resuming a "trial" session
                if (matcher.group(1).equals(localDate)) {
                    trialRunTime = Long.valueOf(matcher.group(2)) + STOPWATCH.getRuntime(TimeUnit.MILLISECONDS);
                } else {
                    // If the Trial Date does Not equal the Local Date, then it is a new day and the botter can use this bot for an hour
                    trialRunTime = STOPWATCH.getRuntime();
                }
            }
        }

        managedProperties.setProperty(key, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + " - " + trialRunTime);

        // Return if our trialRunTime is less than an hour
        return trialRunTime < 3600000 || isPrivate();
    }


}
