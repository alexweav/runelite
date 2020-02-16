package net.runelite.client.plugins.bog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GroundObjectChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


@PluginDescriptor(
        name = "Bog",
        description = "Helper for the Temple Trekking bog event.",
        tags = {"temple", "trekking", "bog"}
)
@Slf4j
public class BogPlugin extends Plugin
{
    private static final int WALKABLE_BOG_TILE_ID = 13838;

    @Getter
    private HashSet<TileObject> firmTiles = new HashSet<>();

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private BogOverlay bogOverlay;

    @Inject
    private Client client;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(bogOverlay);
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event)
    {
        addTileIfFirm(event.getGameObject());
    }

    @Subscribe
    public void onGameObjectChanged(GameObjectChanged event)
    {
        removeTileIfFirm(event.getPrevious());
        addTileIfFirm(event.getGameObject());
    }

    @Subscribe
    public void onGameObjectDespawned(GameObjectDespawned event)
    {
        firmTiles = new HashSet<>();
        //removeTileIfFirm(event.getGameObject());
    }


    @Subscribe
    public void onGroundObjectSpawned(GroundObjectSpawned event)
    {
        addTileIfFirm(event.getGroundObject());
    }

    @Subscribe
    public void onGroundObjectChanged(GroundObjectChanged event)
    {
        removeTileIfFirm(event.getPrevious());
        addTileIfFirm(event.getGroundObject());
    }

    @Subscribe
    public void onGroundObjectDespawned(GroundObjectDespawned event)
    {
        firmTiles = new HashSet<>();
        //removeTileIfFirm(event.getGroundObject());
    }

    /*@Subscribe
    public void onWallObjectSpawned(WallObjectSpawned event)
    {
    }

    @Subscribe
    public void onWallObjectChanged(WallObjectChanged event)
    {
    }

    @Subscribe
    public void onWallObjectDespawned(WallObjectDespawned event)
    {
    }*/

    @Subscribe
    public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
    {
        addTileIfFirm(event.getDecorativeObject());
    }

    @Subscribe
    public void onDecorativeObjectChanged(DecorativeObjectChanged event)
    {
        removeTileIfFirm(event.getPrevious());
        addTileIfFirm(event.getDecorativeObject());
    }

    @Subscribe
    public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
    {
        firmTiles = new HashSet<>();
        //removeTileIfFirm(event.getDecorativeObject());
    }

    private void removeTileIfFirm(TileObject tile)
    {
        if (tile != null)
        {
            firmTiles.remove(tile);
        }
    }

    private void addTileIfFirm(TileObject tile)
    {
        if (tile != null)
        {
            if (tile.getId() == WALKABLE_BOG_TILE_ID)
            {
                firmTiles.add(tile);
            }
        }
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(bogOverlay);
    }
}
