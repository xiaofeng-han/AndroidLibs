FlowLayoutManager Readme
===

Overview
===
FlowLayoutManager is a layoutManager that works with Android ___RecyclerView___ Widget, to layout views in flow style, with support of ___predictive animations___, item per line limit, auto measurement & alignments (left or right)

How to use
===
setup
---
Add following to your build.gradle dependencies section
```gradle
compile 'com.xiaofeng.android:flowlayoutmanager:1.2.3.2'
```

Basic layout
---
```java
recyclerView.setLayoutManager(new FlowLayoutManager());
```

Predictive animation
---
No setup needed, when data changed, do not use notifyDatasetChanged, which will skip all animations, but use notifyItemXXX APIs, those APIs will trigger animations.

Item per line limitation
---
Single item per line
```java
recyclerView.setLayoutManager(new FlowLayoutManager().singleItemPerLine());
```
or x items per line
```java
recyclerView.setLayoutManager(new FlowLayoutManager().maxItemsPerLine(x));
```

to remove Item per line limitation
```java
((FlowLayoutManager)recyclerView.getLayoutManager()).removeItemPerLineLimit();
```

Auto measurement
---
```java
FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
flowLayoutManager.setAutoMeasureEnabled(true);
recyclerView.setLayoutManager(flowLayoutManager);
```

Alignment
---
```java
recyclerView.setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT));
```
Alignment could be LEFT or RIGHT.

License
=======

    Copyright 2016 Xiaofeng Han

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
