# Introduction #

The project has following features implemented.

  * Visualizing a Mind-Map in 3D with spherical layout
  * Basic touch gestures to  manipulate the Mind-Map in 3D
  * Add or remove nodes
  * Save to and load from XML


# Details #

**Visualizing a Mind-Map in 3D with spherical layout**

The design will employ the 3D space to view a Mind-Map which can be represented in a 2D tree structure. In 3D the spherical layout will be used to efficiently occupy the 3D space. The degree of freedom to add a new node at a given level be 2D, but when moving on to the next level, it will occupy the unused dimension to occupy the new nodes, thus avoiding overlapping of nodes and edges. The position of a newly entered node is automatically calculated avoiding any overlapping using the VeHo algorithm. Therefore, the user does not need to worry about overlapping.

**Basic touch gestures to  manipulate the Mind-Map in 3D**

The following basic touch gestures have been implemented on this model.
  * Tap to select
  * Touch and hold, then move to rotate around the centre
  * Drag to move sideways

**Add or remove nodes**

To add a new node, select the node you want to add a child node to.

**Save to and load from XML**

The Mind-Map can be saved in out XML format in sdcard, so it can be loaded later. The XML structure is self explanatory, do have a look.