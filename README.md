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
  - [Модификатор лута](#модификатор-лута);
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
### Интеграция в GatherDataEvent:
Ничего особенного. Это серверные данные!  
Осталось только запустить **runData**.
