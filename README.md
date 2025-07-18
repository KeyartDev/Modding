# Подсказки
## Содержание
- [Предмет](#предмет);
- [Блок](#блок);
- [Теги](#теги):
- [Генерация данных](#генерация-данных);
  - [Генерация моделей блоков/предметов](#генерация-моделей-блоковпредметов);
  - [Генерация рецептов](#генерация-рецептов);
  - [Генерация тегов](#генерация-тегов);
  - [Генерация лут-таблиц](#генерация-лут-таблиц);
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
> ***Зачем нужна такая запись?**
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
