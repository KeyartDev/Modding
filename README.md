# Подсказки
## Содержание
- [Предмет](#предмет);
  - [Стандартный предмет](#создание);
  - [Создание предмета с 2D моделью в инвентаре и с 3D моделью в руке](#создание-предмета-с-2d-моделью-в-инвентаре-и-с-3d-моделью-в-рукекак-у-подзорной-трубы);
  - [Инструменты](#инструменты);
  - [Еда](#еда).
- [Блок](#блок):
  - [Стандартный блок](#создание-1);
  - [Растения](#растенияурожай);
  - [Цветок и цветок в горшке](#цветок-и-цветок-в-горшке).
- [Теги](#теги):
- [Генерация данных](#генерация-данных);
  - [Генерация моделей блоков/предметов](#генерация-моделей-блоковпредметов);
  - [Генерация рецептов](#генерация-рецептов);
  - [Генерация тегов](#генерация-тегов);
  - [Генерация лут-таблиц](#генерация-лут-таблиц);
  - [Модификатор лута](#модификатор-лута).
- [Селяне](#селяне):
  - [Изменение торговли](#изменение-торговли);
  - [Создание собственной профессии](#создание-собственной-профессии);
- [Звуки](#звуки):
  - [Добавление звука](#добавление-звука);
  - [Добавление музыкальной пластинки](#добавление-музыкальной-пластинки).
- [Привязка клавиш](#привязка-клавиш);
- [Передача данных между клиентом и сервером](#передача-данных-между-клиентом-и-сервером);
- [Добавление свойств игроку](#добавление-свойств-игроку);
- [Команда](#команда);
- [GUI(Оверлей)](#guiоверлей).
## Предмет
### Создание
- Добавить класс-регистратор и зарегестрировать его в основном классе мода;
- Зарегистрировать предмет;
- Добавить перевод;
- Добавить модель и текстуру.
Формат json-файла модели предмета выглядит так:
```json
{
  "parent": "item/generated",
  "textures": {
    "layer0": "MOD_ID:block/item"
  }
}
```
### Свойства предмета
  У предмета есть следующие свойства:
- stacksTo(int)$
- fireResistant();
- rarity(Rarity);
- durability(int);
- craftReminder(Item);
- defaultDurability(int);
- food(FoodProperies);
- setNoRepair().
### Создание предмета с 2D моделью в инвентаре и с 3D моделью в руке(как у подзорной трубы)
Чтобы провернуть такое, понадобится 2D текстура и 3D модель предмета(в формате json). В папке **models** нужно создать файл, который повторяет id предмета. Например *magic_wand*. Чтобы иметь 2 разных вида будет использован особый загрузчик от forge: "**forge:separate_transforms**". Тогда файл magic_wand.json будет выглядеть так:
```json
{
  "parent": "minecraft:item/handheld",
  "loader": "forge:separate_transforms",
  "base": {
    "parent": "mod_id:item/magic_wand_3d"
  },
  "perspectives": {
    "gui": {
      "parent": "mod_id:item/magic_wand_2d"
    },
    "ground": {
      "parent": "mod_id:item/magic_wand_2d"
    },
    "fixed": {
      "parent": "mod_id:item/magic_wand_2d"
    }
  }
}
```
"Основой" выступает 3D модель, а 2D текстура будет использована, если:
- gui: она в инвентаре;
- ground: выброшена на землю;
- fixed: где-то зафиксирована(например в рамке, или в других похожих местах).

Содержание "*mod_id:magic_wand_2d*":
```json
{
  "parent": "minecraft:item/handheld",
  "textures": {
    "layer0": "mod_id:item/magic_wand"
  }
}
```
А файл"*mod_id:magic_wand_3d*" - это файл, который мы получили из программы, где создали 3D модель.

***Вот и всё!***
### Инструменты
Для регистрации меча, кирки, лопаты, топора, мотыги используются соответсвующие классы: SwordItem, PickaxeItem, ShovelItem, AxeItem, HoeItem.  
Первым параметром идёт **Tier** предмета(дерево, камень, золото, железо, алмаз, незерит). Также можно создать свой тир.  

**Создание своего тира**  
Для этого в отдельном классе необходимо создать константу типа Tier:
```java
public class EToolTiers {
    public static final Tier SOME = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1500, 5f, 4f, 25,
                    CustomBlockTags.NEEDS_SOME_TOOL, () ->
                    Ingredient.of(ItemRegistry.SOME_BLOCK_FRAG.get())),
            ResourceLocation.fromNamespaceAndPath(Example.MODID, "some_frag"),
            List.of(Tiers.NETHERITE), List.of()
    );
}
```
- TierSortingRegistry - это класс-регистратор, который определяет свойства тира и его положение относительно других тиров;
- registerTier() - непосредственно функция регистрирующая тир;
  - ForgeTier - первый параметр, класс тиров;
    1. Уровень(у незеритового - 4);
    2. Использования(базовое значение);
    3. Скорость(базовая скорость);
    4. Бонус урона;
    5. Значение зачарования(хз что это);
    6. Тег для тира(нужно создать новый);
    7. Ингредиент для починки.
  - id тега;
  - Список тиров перед нашим тиром;
  - Список тиров после нашего тира(В нашем случае наш тир самый высокий).
  
**Генерация моделей**  
Ничего особенного, просто handheldItem().

Теперь если мы захотим, чтобы блок добывался нашей новой киркой, нужно будет просто добавить соответсвующий тег.
### Еда
Создание еды отличается только регистрацией предмета:
```java
public static final RegistryObject<Item> STRAWBERRY =
            ITEMS.register("strawberry", () ->
                    new Item(new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .alwaysEat()
                                    .nutrition(1)
                                    .saturationMod(0.5f).build())));
```
В свойствах просто нужно указать, что это еда с помощью *.food()* и в свойствах указать необходимые данные. 
## Блок
### Создание
- Добавить класс-регистратор и зарегестрировать его в основном классе мода;
- Зарегистрировать блок и предмет-блок(класс BlockItem);
- Добавить перевод;
- Добавить модель и текстуру;
- Добавить состояния блока(assets/MOD_ID/blockstates).
Формат json-файла модели блока, если все стороны имеют одинаковую текстуру:
```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "MOD_ID:block/block"
  }
}
```
Формат json файла бока с 1 состоянием:
```json
{
  "variants": {
    "": {
      "model": "MOD_ID:block/block"
    }
  }
}
```

> [!TIP]
> Для удобства можно сделать функции-помощники, которые будут регестрировать и блок, и предемет сразу:
> ```java
> public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
>     RegistryObject<T> toReturn = BLOCKS.register(name, block);
>     registerBlockItem(name, toReturn);
>     return toReturn;
> }
>
> public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
>     return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
> }
> ```
### Свойства блока
  У блока есть следующие свойства:
- noParticlesOnBreak();
- requiredCorrectToolForDrops();
- noCollision();
- noOcclusion();
- sound(SoundType);
- destroyTime(float);
- air();
- dynamicShape();
- emissiveRendering(StatePredicate);
- explosionResistance(float);
- forceSolidOn();
- friction();
- ignitedByLava();
- instabreak();
- instrument(NoteBlockInstrument);
- jumpFactor(float);
- lightLevel(ToIntFunction<BlockState>);
- lootFrom(Supplier<? extends Block>);
- mapColor(DyeColor/MapColor);
- noLootTable();
- offSetType(OffsetType);
- pushReaction(PushReaction);
- randomTicks();
- replacable();
- speedFactor(float).
### Растения(урожай)
Для начала создадим отдельный класс, наследуемый от **CropBlock** и перегрузим методы getBaseSeedId, getAgeProperty, getMaxAge, createBlockStateDefinition:
```java
public class StrawberryCropBlock extends CropBlock {
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    public StrawberryCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ItemRegistry.STRAWBERRY_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
```  
- MAX_AGE - это константа, показывающая максимальный возраст(стадию роста) растения;
- AGE - это ***свойство***, также показывающее максимальный возраст.  
В чём разница? Всё дело в том, что в мире может существовать множесто блоков этого типа, но с разными стадиями, а MAX_AGE - это константа. В отличии от неё, AGE - это ***свойство**, которое мы добавляем растению с помощью перегруженного метода createBlockStateDifinition(). Что позволит отслеживать стадию роста для изменения текстуры модели в зависимости от стадии.

**Регистрация**  
Тут ничего сложного:
```java
public static final RegistryObject<Block> STRAWBERRY_CROP =
            BLOCKS.register("strawberry_crop", () ->
                    new StrawberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
```
> [!IMPORTANT]
> В свойствах нашего растения обязательно должно быть noCollision() и noOcclusion()!

**Выпадение урожая**  
Тут всё немного сложней:
```java
LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(BlockRegistry.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));

add(BlockRegistry.STRAWBERRY_CROP.get(),
                createCropDrops(BlockRegistry.STRAWBERRY_CROP.get(), ItemRegistry.STRAWBERRY.get(), ItemRegistry.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));
```
Билдер лут-таблицы createCropDrops() в качестве последнего параметра принимает *билдер условия лут-таблиц*. В нашем примере мы создали **lootitemcondition$builder**. В нём мы проверяем достигло ли растение последней сталии роста.  
В качестве урожая мы испоьзовали предмет *клубника*.

**Регистрация семян**  
Тут зарегестрировать предмет, образованный от ItemNameBlockItem:
```java
public static final RegistryObject<Item> STRAWBERRY_SEEDS =
            ITEMS.register("strawberry_seeds", () ->
                    new ItemNameBlockItem(BlockRegistry.STRAWBERRY_CROP.get(), new Item.Properties()));
```

**Образование модели и состояний растения**  
Для этого пришлось создать дополнительные методы:
```java
private void makeStrawberryCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
}

private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/" + textureName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
}
```
Что эти методы делают:
- strawberryStates(): принимает стадию роста растения, блок растения, название модели и текстуры, и на их основе возвращает объект **ConfiguredModel**, который содержит в себе модель заданного состояния. В конце названия текстуры состояние должен стоять номер состояния без пробелов и подчёркиваний(Нумирация начинается с 0!).
- makeStrawberryCrop(): с помощью встроенной в класс BlockStateProvider функции getVariantBuilder(), формирует json файл модели со всеми состояниями(стадиями роста) растения, перебирая каждое и применяя к нему функцию strawberryStates().
> [!NOTE]
> Тип рендера для текстуры мы используем "*cutout*", т.к. в ней обязательно будет присутствовать альфа-канал(Прозрачность).

Что касается модели предмета, то там достаточно использовать basicItem(). Осталось запустить **runData()**!
### Цветок и цветок в горшке
В названии не зря указаны и цветок, и цветок в горшке. ведь с точки зрения майнкрафта, это 2 разных блока, поэтому и регистрировать придётся 2 блока:
```java
public static final RegistryObject<Block> SEVEN_COLOR =
            registerBlock("seven_color_flower", () ->
                    new FlowerBlock(() -> MobEffects.LUCK, 5,
                            BlockBehaviour.Properties.copy(Blocks.ALLIUM)
                                    .noCollission()
                                    .noOcclusion())
            );

public static final RegistryObject<Block> SEVEN_COLOR_POTTED =
            BLOCKS.register("potted_seven_color_flower", () ->
                    new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), BlockRegistry.SEVEN_COLOR,
                            BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)
                                    .noOcclusion())
            );
```
В регистрации может смутить 1-й параметр регистрации цветка - эффект. Это значение используется при приготовлении *подозрительного рагу*.
> [!IMPORTANT]
> Обратите внимание, что в первом случае мы используем *registerBlock()*, а во втором - *BLOCKS.register()*, а также, что во втором мы не используем *noCollision()*. Это важно!

**Дроп из цветка и цветка в горшке**  
Здесь всё просто. Цветок дропает самого себя, а для цыетка в горшке есть отдельный билдер с 1 параметром:
```java
dropSelf(BlockRegistry.SEVEN_COLOR.get());
add(BlockRegistry.SEVEN_COLOR_POTTED.get(), createPotFlowerItemTable(BlockRegistry.SEVEN_COLOR.get()));
```

**Модель цветка и цветка в горшке**  
Тут всё чуть-чуть сложнее, чем с обычным блоком:
```java
simpleBlockWithItem(BlockRegistry.SEVEN_COLOR.get(), models().cross(blockTexture(BlockRegistry.SEVEN_COLOR.get()).getPath(),
                blockTexture(BlockRegistry.SEVEN_COLOR.get())).renderType("cutout"));
simpleBlockWithItem(BlockRegistry.SEVEN_COLOR_POTTED.get(), models().singleTexture("potted_seven_color_flower", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(BlockRegistry.SEVEN_COLOR.get())).renderType("cutout"));
```
В первом случае мы используем тип модели *cross()*, что значит крест-накрест, а во втором случае *singleTexture()*, где в качестве родителя мы указываем "*flower_pot_cross*".  
> [!NOTE]
> Тип рендера для текстуры мы используем "*cutout*", т.к. в ней обязательно будет присутствовать альфа-канал(Прозрачность).

**Модель предмета цветка**  
Для него мы будем брать текстуру не из "**item/", а из "*block/*", поэтому создадим новый метод для таких случаев:
```java
private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/" + item.getId().getPath()));
}
```

**Последние штрихи**  
Чтобы при нажатии ПКМ по цветочному горшку цветок помещался в него, нужно сделать одну необычную вещь в главном классе. Для начала нужно проверить есть ли у вас эта строчка в конструкторе класса:
```java
modEventBus.addListener(this::commonSetup);
```
А затем добавить следующий код в commonSetup():
```java
event.enqueueWork(() -> {
           ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockRegistry.SEVEN_COLOR.getId(), BlockRegistry.SEVEN_COLOR_POTTED);
});
```
Таким образом мы сообщаем, что наш цветок предназначен для посадки в цветочный горшок. Осталось только запустить *runData()*!
## Теги
### Что это?
  Теги - это система группировки игровых объектов (блоков, предметов, сущностей) по логическим категориям.
  Теги бывают нескольких типов:
- Блоки: BlockTags;
- Предметы: ItemTags;
- Жидкости: FluidTags;
- Сущности: EntityTypeTags.
### Создание

  Чтобы создать свой тег. необходимо:
- В отдельном классе создать константу типа TagKey<>, которая будет хранить ключ тега:
```java
public static final TagKey<Block> STONE_BRICKS = tag("stone_rocks");

private static TagKey<Block> tag(String name) {
  return BlockTags.create(
    ResourceLocation.fromNamespaceAndPath(Example.MODID, name)
  );
}
```
  После этого тег можно будет использовать посредством обращения к константе через класс.
## Генерация данных
### Что это?

  **Data Generators** (Datagen) — система Forge для автоматической генерации JSON-файлов (моделей, рецептов, тегов и т.д.).
### Зачем это нужно?
• Устранение ручной работы: Не нужно создавать сотни JSON-файлов вручную.
• Снижение ошибок: Валидация данных происходит на этапе компиляции.
• Кросс-модовая совместимость: Единые стандарты для тегов и рецептов.
### Как это работает?
1. Вы описываете данные через Java-классы (например: "все титановые инструменты должны использовать модель 
item/handheld").
2. При запуске задачи **runData** Forge вызывает событие 
GatherDataEvent.
3. Ваши генераторы создают JSON-файлы в папке **generated/resources**.
### Генерация моделей блоков/предметов
Прежде всего необходимо создать общий класс, где будут регестрироваться все провайдеры(Генераторы данных). К этому классу обязательно нужно добавить аннотацию "@Mod.EventBusSubscriber(modid = Example.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)" и переопределить событие "GatherDataEvent". Также нужно разделить регистрацию серверных и клиентских провайдеров:
> ```java
> @Mod.EventBusSubscriber(modid = Example.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
> public class EDataGen {
>   @SubscribeEvent
>   public static void gatherData(GatherDataEvent event) {
>     DataGenerator generator = event.getGenerator();
>     PackOutput packOutput = generator.getPackOutput();
>     ExistingFileHelper fileHelper = event.getExistingFileHelper();
>     CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
>
>     if (event.includeClient())
>       addClientProviders(generator, packOutput, fileHelper);
>        
>     if (event.includeServer())
>       addServerProviders(generator, packOutput, fileHelper, lookupProvider);
>   }
> 
>   private static void addClientProviders(DataGenerator generator, PackOutput packOutput,
>                                           ExistingFileHelper fileHelper) {
>        //Клиентские провайдеры
>    }
>
>    private static void addServerProviders(DataGenerator generator, PackOutput packOutput,
>                                           ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> lookupProvider) {
>       //Серверные провайдеры
>    }
> }
> ```
> - generator - Управляет процессом генерации данных;
> - packOutput - Определяет, куда сохранять сгенерированные файлы (например, в generated/resources);
> - fileHelper - Проверяет существование файлов (текстур, моделей);
> - lookupProvider - Предоставляет доступ к игровым регистрам (например, список всех блоков или предметов).

После всех вышеперечисленных действий нужно создать отдельный класс для регистрации моделей блоков, который наследуется от BlockStateProvider:
```java
public class EBlockStateProvider extends BlockStateProvider {
  public EBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, Example.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    //Тут будет регистрация моделей
  }

  private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
    simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
  }
}
```
> [!TIP]
> Чтобы регистрировался и блок, и его блок-предмет, создан метод blockWithItem.

Таким образом, чтобы зарегестрировать модель блока, достаточно вписать в метод registerStatesAndModels метод blockWithItem и в качестве параметра передать зарегестрированную константу.

Для предмета всё тоже самое, только методы будут отличаться: для обычного предмета - basicItem, а для инструмента:
```java
private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
  return withExistingParent(item.getId().getPath(),
    ResourceLocation.parse("item/handheld"))
    .texture("layer0",
      ResourceLocation.fromNamespaceAndPath(Example.MODID, "item/" + item.getId().getPath()));
}
```

Осталось только зарегестрировать эти провайдеры:
```java
private static void addClientProviders(DataGenerator generator, PackOutput packOutput,
                                           ExistingFileHelper fileHelper) {
  generator.addProvider(true, new EBlockStateProvider(packOutput, fileHelper));
  generator.addProvider(true, new EItemModelProvider(packOutput, fileHelper));
}
```
### Генерация рецептов
Снова создаём отдельный класс и наследуем его от RecipeProvider:
```java
public class ERecipeProvider extends RecipeProvider {
  public ERecipeProvider(PackOutput pOutput) {
    super(pOutput);
  }

  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
    //Тут будут регистрироваться рецепты    
  }
}
```
Методы создания рецептов находятся в классах:
- ShapedRecipeBuilder;
- ShapelessRecipeBuilder;
- SimpleCookingRecipeBuilder.
Пример создания форменного рецепта:
```java
ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MAGIC_WAND.get(), 1)
  .pattern("gdg")
  .pattern(" g ")
  .pattern(" s ")
  .define('g', Items.GOLD_INGOT)
  .define('d', Items.DIAMOND)
  .define('s', Items.STICK)
  .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
  .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shaped/" + getItemName(ItemRegistry.MAGIC_WAND.get())));
```
Пример создания бесформенного рецепта:
```java
ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MYSTIC_CLOCK.get(), 1)
  .requires(Items.CLOCK)
  .requires(ItemRegistry.SOME_BLOCK_FRAG.get())
  .unlockedBy("hasSomeBlockFrag", has(ItemRegistry.SOME_BLOCK_FRAG.get()))
  .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shapeless/" + getItemName(ItemRegistry.MYSTIC_CLOCK.get())));
```
Пример создания рецепта плавки в печи:
```java
SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.SOME_BLOCK_ORE.get()),
  RecipeCategory.MISC, ItemRegistry.SOME_BLOCK_FRAG.get(), 2.5f, 200)
  .unlockedBy("hasSomeBlockOre", has(BlockRegistry.SOME_BLOCK_ORE.get()))
  .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "smelted/" + getItemName(BlockRegistry.SOME_BLOCK_ORE.get())));
```
Регистрация провайдера **на серверной стороне** в методе addServerProviders:
```java
generator.addProvider(true, new ERecipeProvider(packOutput));
```
### Генерация тегов
Класс-провайдер для тегов блоков, наследующийся от BlockTagsProvider:
```java
public class EBlocksTagProvider extends BlockTagsProvider {
  public EBlocksTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              @Nullable ExistingFileHelper existingFileHelper) {
  super(output, lookupProvider, Example.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    registerMineableTags();
    registerToolRequirementsTags();

    tag(CustomBlockTags.STONE_BRICKS)
      .add(Blocks.STONE)
      .add(Blocks.ANDESITE)
      .add(Blocks.DIORITE)
      .add(Blocks.GRANITE);
  }

  private void registerMineableTags() {
    tag(BlockTags.MINEABLE_WITH_PICKAXE)
      .add(BlockRegistry.EXAMPLE_BLOCK.get())
      .add(BlockRegistry.SOME_BLOCK_ORE.get());
  }

  private void registerToolRequirementsTags() {
    tag(BlockTags.NEEDS_IRON_TOOL)
      .add(BlockRegistry.SOME_BLOCK_ORE.get())
      .add(BlockRegistry.EXAMPLE_BLOCK.get());
  }
}
```
Для тегов предметов тоже самое, но наследуется от ItemTagsProvider.
Решистрация провайдеров:
```java
EBlocksTagProvider blocksTagProvider = generator.addProvider(true, new EBlocksTagProvider(packOutput, lookupProvider, fileHelper));
generator.addProvider(true, new EItemsTagProvider(packOutput, lookupProvider, blocksTagProvider.contentsGetter(), fileHelper));
```
> [!NOTE]
> **Зачем нужна такая запись?**
> - Теги предметов часто зависят от тегов блоков (например: logs → log_items)
> - Генерация должна происходить в строгом порядке:
>   - Сначала теги блоков
>   - Затем теги предметов (используя готовые данные блоков)
> - Без синхронизации возможны ошибки вида:
> Tag minecraft:logs references unknown block: terraria:ebony_log

## Генерация лут-таблиц
Класс провайдера, наследуемый от BlockLootSubProvider:
```java
public class EBlockLootProvider extends BlockLootSubProvider {
    public EBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegistry.EXAMPLE_BLOCK.get());
        dropSelf(BlockRegistry.FUNC_BLOCK.get());

        addOreDrop(BlockRegistry.SOME_BLOCK_ORE.get(), ItemRegistry.SOME_BLOCK_FRAG.get(), 1, 2);
    }

    private void addOreDrop(Block oreBlock, Item drop, int min, int max) {
        add(oreBlock, createSilkTouchDispatchTable(oreBlock,
                applyExplosionDecay(oreBlock,
                        LootItem.lootTableItem(drop)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Stream.of(
                BlockRegistry.BLOCKS.getEntries().stream()
        )
                .flatMap(Function.identity())
                .map(RegistryObject::get)
                .collect(Collectors.toList());
    }
}
```
> [!IMPORTANT]
> **getKnownBlocks()**: Forge использует этот список, чтобы понять, для каких блоков должна быть сгенерирована таблица. Если блока нет в этом списке, но вы попытались сгенерировать для него лут - будет ошибка. Возвращаем все блоки вашего мода.

Регистрация провайдера:
```java
generator.addProvider(true, new LootTableProvider(packOutput, Set.of(), List.of(
    new LootTableProvider.SubProviderEntry(EBlockLootProvider::new, LootContextParamSets.BLOCK))));
```
> [!NOTE]
> Причины "странной" регистрации:
> 1. Поддержка разных типов контекстов
> Лут-таблицы могут использоваться для:
> - Блоков (LootContextParamSets.BLOCK)
> - Сущностей (LootContextParamSets.ENTITY)
> - Сундуков (LootContextParamSets.CHEST)
> Конструктор LootTableProvider принимает список SubProviderEntry, чтобы зарегистрировать несколько типов генераторов.
> 2. Иерархическая структура
> Система лут-таблиц построена на:
> - Главный провайдер (LootTableProvider): Координатор генерации
> - Специализированные субпровайдеры (BlockLootSubProvider): Генераторы для конкретных типов
> 3. Разделение ответственности
> - LootTableProvider обрабатывает сериализацию и запись файлов
> - BlockLootSubProvider содержит логику генерации именно для блоков
## Модификатор лута
### Как это работает?
Модификаторы применяются ПОСЛЕ генерации основного лута, позволяя:
- Добавлять новые предметы
- Удалять существующие
- Изменять количество
- Применять условия (только ночью, только с определённым инструментом).
### Создание класса AddItemModifier
В отличии от ***генерации лута***, для модификации требуется сделать больше действий. Для начала нужно создать дополнительный класс, который и будет изменять лут. Этот класс будет наследоваться от LootModifier:
```java
public class AddItemModifier extends LootModifier {

    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst)
            .and(ForgeRegistries.ITEMS.getCodec()
                    .fieldOf("item")
                    .forGetter(m -> m.item))
            .apply(inst, AddItemModifier::new)));

    private final Item item;

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }
```
**Разбор кода по частям:**
1. Наследование от LootModifier
- LootModifier - базовый класс Forge для всех модификаторов
- Предоставляет систему условий и базовую логику

2. Codec - что это и зачем?
Codec - это система Minecraft для преобразования:
- Java объект → JSON (сериализация)
- JSON → Java объект (десериализация)
Suppliers.memoize() - создаёт кодек только один раз (ленивая инициализация)
3. RecordCodecBuilder - построитель кодека
**Разбор по строкам:**
- codecStart(inst) - начинает построение, добавляет поле conditions
- .and() - добавляет новое поле к кодеку
- ForgeRegistries.ITEMS.getCodec() - кодек для предметов
- .fieldOf("item") - имя поля в JSON будет "item"
- .forGetter(m -> m.item) - как получить значение из объекта
- .apply(inst, AddItemModifier::new) - как создать объект.

Теперь нужно имплементировать необходимые методы:
```java
 @Override
protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return null;
}

@Override
public Codec<? extends IGlobalLootModifier> codec() {
        return null;
}
```
Настройка основной логики метода **doApply()**:
```java
@Override
protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition lootItemCondition : this.conditions) {
            if (!lootItemCondition.test(context)) {
                return generatedLoot;
            }
        }

        generatedLoot.add(new ItemStack(this.item));

        return generatedLoot;
}
```
**Параметры метода**:
- generatedLoot - список уже сгенерированного лута
- context - контекст (кто убил, где, каким инструментом)

**Логика**:
1. Проверяем все условия (если есть)
2. Если хоть одно не выполнено - возвращаем лут без изменений
3. Добавляем наш предмет в конец списка

Метод **codec()**:
```java
@Override
public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
}
```
### Регистрация лут модификаторов
Содержимое класса-регистратора:
```java
public class LootModifiersRegistry {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Example.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);

    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
```
> [!IMPORTANT]
> Мы регистрируем не сам модификатор, а его сериализатор (Codec)!  
> Не забудьте добавить **register()** в главный класс!
### Что такое GlobalLootModifierProvider?
**Архитектура и назначение**:  
GlobalLootModifierProvider - это специальный класс Forge для DataGen, который автоматизирует создание JSON-файлов для лут модификаторов.  

**Зачем он нужен?**  
Без провайдера (ручной способ):  
Вам пришлось бы создавать вручную:
1. Файл data/forge/loot_modifiers/global_loot_modifiers.json
2. Отдельный JSON для каждого модификатора
3. Следить за правильной структурой и синтаксисом 
С провайдером (автоматический способ):  
1. Пишете Java-код
2. Запускаете runData
3. Все файлы генерируются автоматически

**Как работает GlobalLootModifierProvider?**  
Как и остальные провайдеры, использует PackOutput в конструкторе.  

**Внутренняя работа провайдера**:  
Когда вы вызываете:  
add("my_modifier", new AddItemModifier(...))  
Провайдер автоматически:  
1. Создаёт файл data/rinova/loot_modifiers/my_modifier.json
2. Сериализует ваш модификатор используя его Codec
3. Добавляет запись в глобальный список модификаторов
4. Проверяет корректность всех путей и идентификаторов
### DataGen провайдер для модификаторов
Создаём новый класс-провайдер, наследуемый от **GlobalLootModifierProvider**:
```java
public class EGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public EGlobalLootModifiersProvider(PackOutput output) {
        super(output, Example.MODID);
    }

    @Override
    protected void start() {
        add("nether_brush_from_fortress",
                new AddItemModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition.builder(
                                        ResourceLocation.parse("minecraft:chests/nether_bridge")
                                ).build(),
                                LootItemRandomChanceCondition.randomChance(0.3f).build()
                        },
                        ItemRegistry.NETHER_BRUSH.get()
                )
        );

        add("mystic_clock_form_shipwreck",
                new AddItemModifier(
                        new LootItemCondition[]{
                                LootTableIdCondition.builder(
                                        ResourceLocation.parse("minecraft:chests/shipwreck_supply")
                                ).build(),
                                LootItemRandomChanceCondition.randomChance(0.2f).build()
                        },
                        ItemRegistry.MYSTIC_CLOCK.get()
                )
        );
    }
}
```
> [!TIP]
> Откуда можно получить пути лут таблиц? Например сундуки, монстры или археология?  
> Для этого пролистайте вниз проекта и найдите External Libraries, после этого найдите библиотеку:  
> **Gradle: net.minecraft:client:extra:1.20.1** и раскройте её.  
> Там по пути ***data/minecraft/loot_tables*** можно будет найти нужные вам лут-таблицы, в которые вы хотите внедрить свои предметы.
> 
**Разбор метода start()**:
1. *Метод add()*
add("hell_brush_from_bastion", модификатор):
- Первый параметр - имя файла (будет hell_brush_from_bastion.json)
- Второй параметр - экземпляр модификатора
2. *LootTableIdCondition*
- Условие: применять только к конкретной лут-таблице
- minecraft:chests/nether_bridge - сундуки адской крепости
3. *Множественные условия*
```java
new LootItemCondition[] {  
условие1,  
условие2  
}
```
- ВСЕ условия должны быть выполнены (логическое И)
- Для иссушителя: это должен быть иссушитель И убит игроком:

**Полезные условия**:
- LootItemKilledByPlayerCondition - убито игроком
- LootItemRandomChanceCondition - случайный шанс
- MatchTool - определённый инструмент
- WeatherCheck - проверка погоды
- TimeCheck - проверка времени

**Интеграция в GatherDataEvent**   
Ничего особенного. Это серверные данные!  
Осталось только запустить **runData**.

## Селяне
### Изменение торговли
Чтобы изменить торговлю селян, нужно перехватить событие *VillagerTradesEvent*:
```java
@Mod.EventBusSubscriber(modid = Example.MODID)
public class EEvents {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ItemRegistry.STRAWBERRY.get(), 1), 10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.STRAWBERRY_SEEDS.get(), 3), 20, 9, 0.035f));
        }

        if (event.getType() == VillagerRegistry.FUNC_EXPERT.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ItemRegistry.IT_ITEM.get(), 1), 1, 15, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(ItemRegistry.MYSTIC_CLOCK.get(), 1), 1, 25, 0.02f
            ));
        }
    }
}
```
Разбор кода:
- event.getType() == VillagerProfession.FARMER - из события берём текущую формируемую профессию и сравниваем её с фермером, чтобы для него добавить торги;
- Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades() - из события вытаскиваем текущие торги;
- trades.get(1).add(...) - добавляем ему торги для 1-го уровня селянина.

> [!TIP]
> У странствующего торговца также можно модифицировать торги. Отличается только перехватываемое событие - *WandererTradesEvent*.

### Создание собственной профессии
Для этого потребуется создать новый класс регистратор, а в нём две отложенные регистроции: *PoiType* и *VillagerProfession*:
```java
public class VillagerRegistry {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, Example.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Example.MODID);


    public static final RegistryObject<PoiType> FUNC_POI =
            POI_TYPES.register("func_poi", () ->
                    new PoiType(ImmutableSet.copyOf(BlockRegistry.FUNC_BLOCK.get().getStateDefinition().getPossibleStates()),
                            1, 1));

    public static final RegistryObject<VillagerProfession> FUNC_EXPERT =
            VILLAGER_PROFESSIONS.register("func_expert", () -> new VillagerProfession("func_expert",
                    poiTypeHolder -> poiTypeHolder.get() == FUNC_POI.get(), poiTypeHolder -> poiTypeHolder.get() == FUNC_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));


    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        VILLAGER_PROFESSIONS.register(bus);
    }
}
```
Также нужен новый провайдер для тегов poi:
```java
public class EPoiTypeTagsProvider extends PoiTypeTagsProvider {
    public EPoiTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Example.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(ResourceLocation.fromNamespaceAndPath(Example.MODID, "func_poi"));
    }
}
```
И не забыть про **текстуру** для селянина вашей профессии!
## Звуки
### Добавление звука
Создаём новый класс-регистратор:
```java
public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Example.MODID);

    public static final RegistryObject<SoundEvent> PIP_SOUND = registerSoundEvents("pip_sound_ev");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Example.MODID, name)));
    }


    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
```
Добавляем звуковой файл в *assets/mod_id/sounds/* и создаём файл **sounds.json** по пути *assets/mod_id/*. Его содержимое:
```json
{
  "id_вашего_звука": {
    "subtitle": "sounds.mod_id.pip_sound",
    "sounds": [
      "mod_id:название_вашего_звукогового_файла"
    ]
  }
}
```
> [!IMPORTANT]
> Все звуковые файлы должны быть в формате ***.ogg** и в **моно-режиме** звучания!

### Добавление музыкальной пластинки
Во-первых добавляем файл музыки в класс-регистратор, а затем в *sounds.json*:
```json
"kind_necro": {
    "sounds": [
      "mod_id:kind_necro"
    ],
    "stream": true
}
```
Регистрируем пластинку:
```java
public static final RegistryObject<Item> KIND_NECRO_DISK =
            ITEMS.register("kind_necro_disk", () ->
                    new RecordItem(6, SoundRegistry.KIND_NECRO, new Item.Properties().stacksTo(1), 3350));
```
Переносим текстуру пластинки в textures/item и добавляем в генератор моделей как basicItem.
### Привязка клавиш
Для определения клавиш нужно создать отдельный класс:
```java
public class KeyBinding {
    public static final String EXAMPLE_KEY_CATEGORY = "key.category.mod_id.example";
    public static final String EXAMPLE_KEY = "key.mod_id.example";

    public static final KeyMapping EXAMPLED_KEY = new KeyMapping(EXAMPLE_KEY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, EXAMPLE_KEY_CATEGORY);
}
```
Чтобы их зарегистрировать, нужно перехватить событие *RegisterKeyMappingsEvent*:
```java
@Mod.EventBusSubscriber(modid = Example.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EClientModBusEvents {
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.EXAMPLED_KEY);
    }
}
```
Теперь, чтобы как-то использовать это, мы перехватим другое событие - *InputEvent.Key* и выведем в чат сообщение:
```java
@Mod.EventBusSubscriber(modid = Example.MODID, value = Dist.CLIENT)
public class EClientEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBinding.EXAMPLED_KEY.consumeClick()) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Key pressed!"));
        }
    }
}
```
> [!IMPORTANT]
> Обратите внимание, что события находятся в разных классах, т.к. *RegisterKeyMappingsEvent* использует шину **Mod.EventBusSubscriber.Bus.MOD**, которая указывается в аннотации:
> ```java
> @Mod.EventBusSubscriber(modid = Example.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
> ```
> Другое же событие - *InputEvent.Key* - использует шину **Forge**, которая используется по умолчанию:
> ```java
> @Mod.EventBusSubscriber(modid = Example.MODID, value = Dist.CLIENT)
> ```
## Передача данных между клиентом и сервером
Этого можно достигнуть путём использования **пакетов**. Создадим класс:
```java ENetworks.java
public class ENetworks {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(Example.MODID, "messages"))
                .clientAcceptedVersions(t -> true)
                .serverAcceptedVersions(t -> true)
                .networkProtocolVersion(() -> "1.0")
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)  //Регистрация пакета
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
```
Класс пакета:
```java
public class ExampleC2SPacket {
    public ExampleC2SPacket() {

    }

    public ExampleC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // ЗДЕСЬ СЕРВЕР!!!
        });

        return true;
    }
}
```
> [!TIP]
> Всё, что находится в пометке "**ЗДЕСЬ СЕРВЕР!!!**", будет выполняться на стороне *сервера*!  
Теперь, чтобы передать пакет на сервер, достаточно написать:
```java 
ENetworks.sendToServer(new ExampleC2SPacket());
```
## Добавление свойств игроку
Для этого понадобится класс свойтсва и провайдер для него.  
Класс свойства(На примере системы жажды):
```java
public class PlayerThirst {
    private int thirst;
    private final int MIN_THIRST = 0;
    private final int MAX_THIRST = 10;

    public int getThirst() {
        return thirst;
    }

    public void addThirst(int add) {
        thirst = Math.min(thirst + add, MAX_THIRST);
    }

    public void subThirst(int sub) {
        thirst = Math.max(thirst - sub, MIN_THIRST);
    }

    public void copyFrom(PlayerThirst source) {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("thirst", thirst);
    }

    public void loadNBTData(CompoundTag nbt) {
        thirst = nbt.getInt("thirst");
    }

    public void setThirst(int set) {
        if (set > MAX_THIRST)
            thirst = MAX_THIRST;
        else if (set < MIN_THIRST) {
            thirst = MIN_THIRST;
        } else {
            thirst = set;
        }
    }
}
```
Класс провайдера:
```java
public class PlayerThirstProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerThirst> PLAYER_THIRST = CapabilityManager.get(new CapabilityToken<PlayerThirst>() { });

    private PlayerThirst thirst = null;
    private final LazyOptional<PlayerThirst> optional = LazyOptional.of(this::createPlayerThirst);

    private @NotNull PlayerThirst createPlayerThirst() {
        if (this.thirst == null)
            this.thirst = new PlayerThirst();

        return this.thirst;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_THIRST)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerThirst().saveNBTData(nbt);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerThirst().loadNBTData(nbt);
    }
}
```
Регистрация свойства происходит в перехватчике событий на стороне сервера с шиной Forge:
```java
@SubscribeEvent
public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Example.MODID, "properties"), new PlayerThirstProvider());
            }
        }
}

@SubscribeEvent
public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
        event.getOriginal().invalidateCaps();
}

@SubscribeEvent
public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
}
```
- onAttachCapabilitiesPlayer() - добавляет свойство игроку;
- onPlayerCloned() - обновляет свойства игрока при смерти;
- onRegisterCapabilities() - регистрирует свойтсво.
## Команда
Для добавления команды создаётся отдельный класс:
```java
public class ThirstCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("thirst").requires(sender -> sender.hasPermission(0))
                .then(Commands.literal("get").executes(commandContext -> {
                    ServerPlayer player = commandContext.getSource().getPlayerOrException();
                    player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        player.sendSystemMessage(Component.literal("Ваша жажда: " + thirst.getThirst()).withStyle(ChatFormatting.DARK_AQUA));
                    });

                    return Command.SINGLE_SUCCESS;
                }))
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("count", IntegerArgumentType.integer(0, 10))
                                        .executes(commandContext -> {
                                            ServerPlayer player = EntityArgument.getPlayer(commandContext, "target");
                                            int count = IntegerArgumentType.getInteger(commandContext, "count");

                                            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                                                thirst.setThirst(count);
                                                ENetworks.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), player);
                                            });

                                            player.sendSystemMessage(Component.literal("Ваша жажда установлена на " + count + "!")
                                                    .withStyle(ChatFormatting.DARK_AQUA));



                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                ));
    }
}
```
Регистрация команды происходит в перехватчике событий на стороне сервера:
```java
@SubscribeEvent
public static void registerCommands(RegisterCommandsEvent event) {
        ThirstCommand.register(event.getDispatcher());
}
```
## GUI(оверлей)
[Ссылка на код GUI на примере жажды](src/main/java/org/keyart/example/common/client/ThirstHudOverlay.java)

Для регистрации используется событие *RegisterGuiOverlayEvent*:
```java
@SubscribeEvent
public static void registerHUD(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
}
```
