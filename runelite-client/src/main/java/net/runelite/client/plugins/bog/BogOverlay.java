package net.runelite.client.plugins.bog;

import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;

public class BogOverlay extends Overlay {

    private final BogPlugin plugin;
    private final Client client;

    private static final Color TILE_HIGHLIGHT_COLOR = Color.GREEN;

    @Inject
    BogOverlay(Client client, BogPlugin plugin)
    {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        plugin.getFirmTiles().forEach(tile -> {
            Polygon polygon = tile.getCanvasTilePoly();
            if (polygon != null)
            {
                OverlayUtil.renderPolygon(graphics, polygon, TILE_HIGHLIGHT_COLOR);
            }
        });

        return null;
    }
}
