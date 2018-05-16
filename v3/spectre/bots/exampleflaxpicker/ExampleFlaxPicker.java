package com.sudo.v3.spectre.bots.exampleflaxpicker;

import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.calculations.CommonMath;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.branches.IsBankOpen;
import com.sudo.v3.spectre.bots.exampleflaxpicker.branches.IsInventoryFull;
import com.sudo.v3.spectre.bots.exampleflaxpicker.branches.IsNearby;
import com.sudo.v3.spectre.bots.exampleflaxpicker.branches.Root;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.BankingType;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.Locatables;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.TraversalLocation;
import com.sudo.v3.spectre.bots.exampleflaxpicker.leafs.*;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ui.FlaxFXGui;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ui.FlaxInfoUI;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ui.Info;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.concurrent.TimeUnit;

/**
 * Created by Proxi on 4/5/2016.
 * - Modified 12/28/16: Updated to reflect TreeBot architecture
 *
 * This bot is designed to give those of you that are new to spectre development
 * some guidance and direction when using some of the new features within RuneMate spectre.
 *
 * This bot will use the new GUI standard for RuneMate known as EmbeddableUI.
 *  - In short, this literally runs your Java FX GUI embedded into spectre.
 *
 * If any questions come up, feel free to ask. Several developers are always active on our website and Slack.
 *
 * - Proxi
 *
 * --- Bot Description ---
 * This will pick and bank flax in Taverly or Seers Village.
 *
 */
public class ExampleFlaxPicker extends TreeBot implements InventoryListener, EmbeddableUI {

    public Info info;

    private FlaxFXGui configUI;
    private FlaxInfoUI infoUI;
    private SimpleObjectProperty<Node> botInterfaceProperty;

    public StopWatch stopWatch = new StopWatch();
    private int flaxCount;
    public String currentTaskString;
    public Boolean guiWait;
    public Player player;

    // Generic Area holders that will be assigned once the user picked a location in the GUI
    public Area flaxArea, bankArea;

    // Supported area locations that we've mapped out
    public final Area TAV_FLAX_AREA = new Area.Rectangular(new Coordinate(2883, 3463, 0), new Coordinate(2889, 3468, 0)),
            TAV_BANK_AREA = new Area.Rectangular(new Coordinate(2877, 3419, 0), new Coordinate(2874, 3415, 0)),
            SEER_FLAX_AREA = new Area.Rectangular(new Coordinate(2745, 3451, 0), new Coordinate(2738, 3438, 0)),
            SEER_BANK_AREA = new Area.Rectangular(new Coordinate(2730, 3491, 0), new Coordinate(2722, 3494, 0));

    // Holds the banking state we're in. Giving it a default value of .depositing
    public BankingType bankingType = BankingType.depositing;

    // Holds the state of which area we're traversing towards. Giving it a default value of .flaxArea
    public TraversalLocation traversalLocation = TraversalLocation.flaxArea;

    // Holds the state of which item we're attempting to locate. Giving it a default value of .flax
    public Locatables itemToLocate = Locatables.flax;

    // Initialize your Branches
    public Root root = new Root(this);
    public IsBankOpen isBankOpenBranch = new IsBankOpen(this);
    public IsInventoryFull isInventoryFullBranch = new IsInventoryFull(this);
    public IsNearby isNearbyBranch = new IsNearby(this);

    // Initialize your Leafs
    public DepositAllLeaf depositAllLeaf = new DepositAllLeaf(this);
    public OpenBankLeaf openBankLeaf = new OpenBankLeaf(this);
    public PickFlaxLeaf pickFlaxLeaf = new PickFlaxLeaf(this);
    public TraversalLeaf traversalLeaf = new TraversalLeaf(this);
    public EmptyLeaf emptyLeaf = new EmptyLeaf();

    // Bot's default constructor
    public ExampleFlaxPicker() {
        // Initialize your variables
        guiWait = true;
        flaxCount = 0;

        // Set this class as the EmbeddableUI
        setEmbeddableUI(this);
    }

    /*
    This is where the magic happens for TreeBot. Start by giving the origin of your tree, in this case we have it named "Root".
    Proceed to the Root class.
     */
    @Override
    public TreeTask createRootTask() {
        // Return our root tree branch
        return new Root(this);
    }

    @Override
    public ObjectProperty<? extends Node> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            // Initializing configUI in this manor is known as Lazy Instantiation
            botInterfaceProperty = new SimpleObjectProperty<>(configUI = new FlaxFXGui(this));
            infoUI = new FlaxInfoUI(this);
        }
        return botInterfaceProperty;
    }

    @Override
    public void onStart(String... args) {
        // Set/Run anything that needs to be ran at the initial start of the bot
        stopWatch.start();
        currentTaskString = "Starting bot...";

        // Sets the length of time in milliseconds to wait before calling onLoop again
        // NOTE: IT IS NOT RECOMMENDED TO KEEP DEFAULT LOOP DELAY
        setLoopDelay(300, 600);

        // Set custom mouse multiplier or leave default
        Mouse.setSpeedMultiplier(1);

        // Force menu interaction when clicking (force right-click interaction)
        // Mouse.setForceMenuInteraction(true);

        // Add this class as a listener for the Event Dispatcher
        getEventDispatcher().addListener(this);
    }


    @Override
    public void onItemAdded(ItemEvent event) {
        ItemDefinition definition = event.getItem().getDefinition();
        if (definition != null) {
            // If an item with the name "Flax" appears in the inventory, increase count by 1
            if (definition.getName().contains("Flax")) {
                flaxCount++;
            }
        }
    }

    // When called, switch the botInterfaceProperty to reflect the InfoUI
    public void setToInfoProperty() {
        botInterfaceProperty.set(infoUI);
    }

    // This method is used to update the GUI thread from the bot thread
    public void updateInfo() {
        try {
            // Assign all values to a new instance of the Info class
            info = new Info(
                    (int) CommonMath.rate(TimeUnit.HOURS, stopWatch.getRuntime(), flaxCount),  //   -   -   -   -   -   -   -   // Flax per hour
                    flaxCount,                //    -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   // Flax Picked
                    stopWatch.getRuntimeAsString(),                 //  -   -   -   -   -   -   -   -   -   -   -   -   -   -   // Total Runtime
                    currentTaskString);       //    -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   // Current Task

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Be sure to run infoUI.update() through runLater.
        // This will run infoUI.update() on the dedicated JavaFX thread which is the only thread allowed to update anything related to JavaFX rendering
        Platform.runLater(() -> infoUI.update());

        /*
        *  "The way to think about it
            is that the "some stuff" is a package
            and you're at your house (the bot thread)
            Platform.runLater is the mailman
            you give the mailman your package and then go about your life however you want
            i.e. keep going in the code
            and then the mailman does what he needs with the package to get it delivered
            and it's no longer your or your house's problem"
            - The Wise. The One. The Arbiter.
         */
    }
}